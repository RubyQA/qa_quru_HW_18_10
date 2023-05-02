import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.io.Files;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTest {


    private final ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    void zippedPdfParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("example.zip")) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;

                while ((entry = zis.getNextEntry()) != null) {
                    switch (Files.getFileExtension(entry.getName())) {
                        case "pdf" -> {
                            PDF pdf = new PDF(zis);
                            Assertions.assertEquals(2, pdf.numberOfPages,
                                    "pdf parsed failed");
                        }
                        case "xlsx" -> {
                            XLS xls = new XLS(zis);
                            Assertions.assertTrue(
                                    xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue().contains("A"),
                                    "xlsx parsed failed");
                        }
                        case "csv" -> {
                            CSVReader reader = new CSVReader(new InputStreamReader(zis, StandardCharsets.UTF_8));
                            List<String[]> content = reader.readAll();
                            Assertions.assertArrayEquals(new String[]{"John", "30", "Male"}, content.get(1),
                                    "csv parsed failed");
                        }
                    }
                }
            }
        }
    }
}
