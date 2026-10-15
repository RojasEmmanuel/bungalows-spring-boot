package com.emmadev.bungalows.service.Documento;

import com.emmadev.bungalows.Enums.TipoDocumento;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

@Service
public class DocumentoPdfServiceImpl implements DocumentoPdfService {

    private final SpringTemplateEngine templateEngine;

    public DocumentoPdfServiceImpl(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public byte[] generarPdf(TipoDocumento tipo, Object modelo) {
        Context ctx = new Context();
        ctx.setVariable("modelo", modelo);
        String html = templateEngine.process(tipo.getPlantilla(), ctx);
        return htmlAPdf(html);
    }

    @Override
    public byte[] generarPdfDesdeHtml(String plantilla, Map<String, Object> modelo) {
        Context ctx = new Context();
        ctx.setVariables(modelo);
        String html = templateEngine.process(plantilla, ctx);
        return htmlAPdf(html);
    }

    private byte[] htmlAPdf(String html) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            registrarFuentes(builder);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new DocumentoGenerationException("Error generando PDF", e);
        }
    }

    private void registrarFuentes(PdfRendererBuilder builder) {
        builder.useFont(
                () -> getClass().getResourceAsStream("/fonts/NotoSans-Regular.ttf"),
                "NotoSans" // Este es el nombre que usarás en el CSS
        );

        builder.useFont(
                () -> getClass().getResourceAsStream("/fonts/NotoSans-Bold.ttf"),
                "NotoSans",
                700, // Peso para bold
                PdfRendererBuilder.FontStyle.NORMAL,
                true
        );
    }
    private void registrar(PdfRendererBuilder builder, String path, String familia) {
        registrar(builder, path, familia, 400);
    }

    private void registrar(PdfRendererBuilder builder, String path, String familia, int weight) {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new IllegalStateException("Fuente no encontrada: " + path);
        }
        builder.useFont(() -> is, familia, weight, PdfRendererBuilder.FontStyle.NORMAL, true);
    }
}