package com.emmadev.bungalows.service.Logos;

import com.emmadev.bungalows.entity.Empresa;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;

@Service
public class LogoServiceImpl implements LogoService {

    private static final Logger log = LoggerFactory.getLogger(LogoServiceImpl.class);

    private final RestClient restClient;
    private final String uploadsDir;
    private final String urlBase;

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public LogoServiceImpl(
            RestClient logoRestClient,
            @Value("${app.uploads.dir}") String uploadsDir,
            @Value("${app.uploads.url-base}") String urlBase) {
        this.restClient = logoRestClient;
        this.uploadsDir = uploadsDir;
        this.urlBase = urlBase.endsWith("/")
                ? urlBase.substring(0, urlBase.length() - 1)
                : urlBase;
    }

    @Override
    public String obtenerLogoBase64(Empresa empresa) {
        if (empresa == null) return "";
        return obtenerLogoBase64(empresa.getLogoPath());
    }

    @Override
    public String obtenerLogoBase64(String logoPath) {
        if (logoPath == null) return "";
        String key = logoPath.trim();
        if (key.isEmpty()) return "";

        return cache.computeIfAbsent(key, this::resolverYConvertir);
    }

    // ---------- Estrategia: primero HTTP, fallback a filesystem ----------

    private String resolverYConvertir(String logoPath) {
        String url = resolverUrl(logoPath);
        String dataUri = descargar(url);
        if (!dataUri.isEmpty()) return dataUri;

        dataUri = leerDeDisco(logoPath);
        if (!dataUri.isEmpty()) return dataUri;

        log.warn("[LOGO] no se pudo resolver el logo: {}", logoPath);
        return "";
    }

    /** Si ya es absoluta la deja; si es relativa, le antepone url-base. */
    private String resolverUrl(String logoPath) {
        if (logoPath.startsWith("http://") || logoPath.startsWith("https://")) {
            return logoPath;
        }
        String rel = logoPath.startsWith("/") ? logoPath : "/" + logoPath;
        return urlBase + rel;
    }

    private String descargar(String url) {
        try {
            byte[] bytes = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(byte[].class);

            if (bytes == null || bytes.length == 0) {
                log.warn("[LOGO] respuesta vacía para {}", url);
                return "";
            }
            return aDataUri(bytes, detectarMime(url));

        } catch (Exception e) {
            log.warn("[LOGO] fallo HTTP {} -> {}", url, e.getMessage());
            return "";
        }
    }

    /** Lee el archivo desde app.uploads.dir + path relativo. */
    private String leerDeDisco(String logoPath) {
        try {
            String rel = extraerPathRelativo(logoPath);
            if (rel == null) return "";

            Path base = Paths.get(uploadsDir).toAbsolutePath().normalize();
            Path file = base.resolve(rel).normalize();

            if (!file.startsWith(base)) {
                log.warn("[LOGO] path fuera del directorio de uploads: {}", file);
                return "";
            }

            if (!Files.exists(file)) {
                log.warn("[LOGO] no existe en disco: {}", file);
                return "";
            }

            byte[] bytes = Files.readAllBytes(file);
            String mime = detectarMime(file.getFileName().toString());
            return aDataUri(bytes, mime);

        } catch (Exception e) {
            log.warn("[LOGO] fallo disco {} -> {}", logoPath, e.getMessage());
            return "";
        }
    }

    /**
     * Convierte:
     *   http://localhost:8080/uploads/empresas/logos/x.png  -> empresas/logos/x.png
     *   /uploads/empresas/logos/x.png                       -> empresas/logos/x.png
     *   empresas/logos/x.png                                -> empresas/logos/x.png
     */
    private String extraerPathRelativo(String logoPath) {
        String p = logoPath.trim();
        if (p.isEmpty()) return null;

        if (p.startsWith("http://") || p.startsWith("https://")) {
            try {
                p = URI.create(p).getPath();
            } catch (Exception e) {
                return null;
            }
        }

        // quitar prefijo /uploads/
        if (p.startsWith("/uploads/")) {
            p = p.substring("/uploads/".length());
        } else if (p.startsWith("uploads/")) {
            p = p.substring("uploads/".length());
        } else if (p.startsWith("/")) {
            p = p.substring(1);
        }
        return p;
    }

    private String aDataUri(byte[] bytes, String mime) {
        return "data:" + mime + ";base64," + Base64.getEncoder().encodeToString(bytes);
    }

    private String detectarMime(String nombreOUrl) {
        String u = nombreOUrl.toLowerCase();
        if (u.endsWith(".jpg") || u.endsWith(".jpeg")) return "image/jpeg";
        if (u.endsWith(".webp")) return "image/webp";
        return "image/png";
    }
}