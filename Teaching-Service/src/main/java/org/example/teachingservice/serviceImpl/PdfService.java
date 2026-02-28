package org.example.teachingservice.serviceImpl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.extern.slf4j.Slf4j;
import org.example.teachingservice.entity.EmploiTemps;
import org.example.teachingservice.entity.JourSemaine;
import org.example.teachingservice.entity.Seance;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Slf4j
@Service
public class PdfService {

    /* ── Couleurs ─────────────────────────────────────────────────── */
    private static final DeviceRgb INDIGO     = new DeviceRgb(79,  70,  229);
    private static final DeviceRgb GREEN_CELL = new DeviceRgb(236, 253, 245);
    private static final DeviceRgb TIME_BG    = new DeviceRgb(224, 231, 255);
    private static final DeviceRgb ROW_EVEN   = new DeviceRgb(243, 244, 246);
    private static final DeviceRgb TEXT_DARK  = new DeviceRgb(31,  41,  55);

    /* ── Ordre des jours ──────────────────────────────────────────── */
    private static final JourSemaine[] JOURS = {
            JourSemaine.LUNDI, JourSemaine.MARDI, JourSemaine.MERCREDI,
            JourSemaine.JEUDI, JourSemaine.VENDREDI, JourSemaine.SAMEDI, JourSemaine.DIMANCHE
    };

    /* ── Créneaux horaires ────────────────────────────────────────── */
    private static final List<LocalTime[]> CRENEAUX = List.of(
            new LocalTime[]{LocalTime.of(8,  0),  LocalTime.of(9,  30)},
            new LocalTime[]{LocalTime.of(9,  30), LocalTime.of(11, 0)},
            new LocalTime[]{LocalTime.of(11, 0),  LocalTime.of(12, 30)},
            new LocalTime[]{LocalTime.of(14, 0),  LocalTime.of(15, 30)},
            new LocalTime[]{LocalTime.of(15, 30), LocalTime.of(17, 0)},
            new LocalTime[]{LocalTime.of(17, 0),  LocalTime.of(18, 30)}
    );

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm");

    /* ════════════════════════════════════════════════════════════════
       PDF individuel
    ════════════════════════════════════════════════════════════════ */
    public byte[] generateEmploiPdf(EmploiTemps et) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc   = openDoc(out);
            PdfFont  bold  = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont  reg   = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            title(doc, "Emploi du Temps", bold);
            subtitle(doc, String.format("%s %s  |  CIN : %s  |  Type : %s  |  Année : %s",
                    et.getTeacher().getFirstName(), et.getTeacher().getLastName(),
                    et.getTeacher().getIdCard(),
                    label(et.getTypeEmploi().name()),
                    et.getAnneeUniversitaire()), reg);

            doc.add(grid(et.getSeances(), bold, reg));
            doc.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erreur PDF individuel", e);
        }
    }

    /* ════════════════════════════════════════════════════════════════
       PDF groupé (tous les teachers d'un type)
    ════════════════════════════════════════════════════════════════ */
    public byte[] generateAllEmploiPdf(List<EmploiTemps> list) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc  = openDoc(out);
            PdfFont  bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont  reg  = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            for (int i = 0; i < list.size(); i++) {
                EmploiTemps et = list.get(i);
                if (i > 0) doc.add(new AreaBreak());
                title(doc, "Emploi du Temps", bold);
                subtitle(doc, String.format("%s %s  |  CIN : %s  |  Année : %s",
                        et.getTeacher().getFirstName(), et.getTeacher().getLastName(),
                        et.getTeacher().getIdCard(), et.getAnneeUniversitaire()), reg);
                doc.add(grid(et.getSeances(), bold, reg));
            }

            doc.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erreur PDF groupé", e);
        }
    }

    /* ── helpers ──────────────────────────────────────────────────── */

    private Document openDoc(ByteArrayOutputStream out) throws IOException {
        Document doc = new Document(new PdfDocument(new PdfWriter(out)), PageSize.A4.rotate());
        doc.setMargins(20, 20, 20, 20);
        return doc;
    }

    private void title(Document doc, String text, PdfFont font) {
        doc.add(new Paragraph(text)
                .setFont(font).setFontSize(16).setFontColor(INDIGO)
                .setTextAlignment(TextAlignment.CENTER).setMarginBottom(4));
    }

    private void subtitle(Document doc, String text, PdfFont font) {
        doc.add(new Paragraph(text)
                .setFont(font).setFontSize(10).setFontColor(TEXT_DARK)
                .setTextAlignment(TextAlignment.CENTER).setMarginBottom(14));
    }

    private Table grid(List<Seance> seances, PdfFont bold, PdfFont reg) {
        Map<JourSemaine, Map<Integer, Seance>> map = buildGrid(seances);

        float[] widths = new float[1 + JOURS.length];
        widths[0] = 8f;
        Arrays.fill(widths, 1, widths.length, 13f);

        Table t = new Table(UnitValue.createPercentArray(widths)).useAllAvailableWidth();

        /* en-tête */
        hCell(t, "Horaire", bold);
        for (JourSemaine j : JOURS) hCell(t, jourFr(j), bold);

        /* lignes */
        for (int ci = 0; ci < CRENEAUX.size(); ci++) {
            LocalTime[] cr = CRENEAUX.get(ci);
            tCell(t, cr[0].format(FMT) + "\n" + cr[1].format(FMT), bold);
            for (JourSemaine j : JOURS) {
                Seance s = map.getOrDefault(j, Collections.emptyMap()).get(ci);
                if (s != null) sCell(t, s, reg);
                else            eCell(t, ci % 2 == 0);
            }
        }
        return t;
    }

    private Map<JourSemaine, Map<Integer, Seance>> buildGrid(List<Seance> seances) {
        Map<JourSemaine, Map<Integer, Seance>> g = new EnumMap<>(JourSemaine.class);
        for (Seance s : seances) {
            int idx = creneauIdx(s.getHeureDebut());
            if (idx >= 0)
                g.computeIfAbsent(s.getJour(), k -> new HashMap<>()).put(idx, s);
        }
        return g;
    }

    private int creneauIdx(LocalTime h) {
        for (int i = 0; i < CRENEAUX.size(); i++) {
            if (CRENEAUX.get(i)[0].equals(h)) return i;
        }
        for (int i = 0; i < CRENEAUX.size(); i++) {
            if (!h.isBefore(CRENEAUX.get(i)[0]) && h.isBefore(CRENEAUX.get(i)[1])) return i;
        }
        return -1;
    }

    private void hCell(Table t, String txt, PdfFont f) {
        t.addHeaderCell(new Cell()
                .add(new Paragraph(txt).setFont(f).setFontSize(9).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(INDIGO).setTextAlignment(TextAlignment.CENTER).setPadding(5));
    }

    private void tCell(Table t, String txt, PdfFont f) {
        t.addCell(new Cell()
                .add(new Paragraph(txt).setFont(f).setFontSize(8).setFontColor(INDIGO))
                .setBackgroundColor(TIME_BG).setTextAlignment(TextAlignment.CENTER).setPadding(4));
    }

    private void sCell(Table t, Seance s, PdfFont f) {
        String c = s.getMatiere()
                + (s.getTypeSeance() != null ? "\n[" + s.getTypeSeance() + "]" : "")
                + (s.getSalle()      != null ? "\nSalle : " + s.getSalle()     : "")
                + (s.getGroupe()     != null ? "\n" + s.getGroupe()            : "");
        t.addCell(new Cell()
                .add(new Paragraph(c).setFont(f).setFontSize(7.5f).setFontColor(TEXT_DARK))
                .setBackgroundColor(GREEN_CELL).setTextAlignment(TextAlignment.CENTER).setPadding(3));
    }

    private void eCell(Table t, boolean even) {
        t.addCell(new Cell().add(new Paragraph(""))
                .setBackgroundColor(even ? ROW_EVEN : ColorConstants.WHITE).setPadding(4));
    }

    private String jourFr(JourSemaine j) {
        return switch (j) {
            case LUNDI    -> "Lundi";
            case MARDI    -> "Mardi";
            case MERCREDI -> "Mercredi";
            case JEUDI    -> "Jeudi";
            case VENDREDI -> "Vendredi";
            case SAMEDI   -> "Samedi";
            case DIMANCHE -> "Dimanche";
        };
    }

    private String label(String type) {
        return switch (type) {
            case "SEMESTRE_1"     -> "Semestre 1";
            case "SEMESTRE_2"     -> "Semestre 2";
            case "FORMATION_JOUR" -> "Formation Jour";
            case "FORMATION_SOIR" -> "Formation Soir";
            case "WEEK_END"       -> "Week-end";
            default -> type;
        };
    }
}