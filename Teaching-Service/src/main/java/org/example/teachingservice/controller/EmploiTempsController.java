package org.example.teachingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.teachingservice.dto.ApiResponse;
import org.example.teachingservice.dto.CreateEmploiTempsRequest;
import org.example.teachingservice.dto.EmploiTempsDTO;
import org.example.teachingservice.dto.TeacherDTO;
import org.example.teachingservice.entity.TypeEmploi;
import org.example.teachingservice.service.EmploiTempsService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emploi")
@RequiredArgsConstructor
@CrossOrigin(origins = "${app.cors.allowed-origins}")
public class EmploiTempsController {

    private final EmploiTempsService service;

    /* ══════════════════════════════════════════════════════════════
       Teachers filtrés par type  →  utilisés par les 5 onglets Angular
    ══════════════════════════════════════════════════════════════ */

    @GetMapping("/teachers/semestre1")
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> teachersSemestre1() {
        return ok(service.getTeachersBySemestre1());
    }

    @GetMapping("/teachers/semestre2")
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> teachersSemestre2() {
        return ok(service.getTeachersBySemestre2());
    }

    @GetMapping("/teachers/formation-jour")
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> teachersFormationJour() {
        return ok(service.getTeachersByFormationJour());
    }

    @GetMapping("/teachers/formation-soir")
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> teachersFormationSoir() {
        return ok(service.getTeachersByFormationSoir());
    }

    @GetMapping("/teachers/week-end")
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> teachersWeekEnd() {
        return ok(service.getTeachersByWeekEnd());
    }

    /* ══════════════════════════════════════════════════════════════
       EmploiTemps complets par type
    ══════════════════════════════════════════════════════════════ */

    @GetMapping("/semestre1")
    public ResponseEntity<ApiResponse<List<EmploiTempsDTO>>> semestre1() {
        return ok(service.getByType(TypeEmploi.SEMESTRE_1));
    }

    @GetMapping("/semestre2")
    public ResponseEntity<ApiResponse<List<EmploiTempsDTO>>> semestre2() {
        return ok(service.getByType(TypeEmploi.SEMESTRE_2));
    }

    @GetMapping("/formation-jour")
    public ResponseEntity<ApiResponse<List<EmploiTempsDTO>>> formationJour() {
        return ok(service.getByType(TypeEmploi.FORMATION_JOUR));
    }

    @GetMapping("/formation-soir")
    public ResponseEntity<ApiResponse<List<EmploiTempsDTO>>> formationSoir() {
        return ok(service.getByType(TypeEmploi.FORMATION_SOIR));
    }

    @GetMapping("/week-end")
    public ResponseEntity<ApiResponse<List<EmploiTempsDTO>>> weekEnd() {
        return ok(service.getByType(TypeEmploi.WEEK_END));
    }

    /* ══════════════════════════════════════════════════════════════
       CRUD
    ══════════════════════════════════════════════════════════════ */

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmploiTempsDTO>> getById(@PathVariable Long id) {
        return ok(service.getById(id));
    }

    @GetMapping("/teacher/{teacherId}/type/{type}")
    public ResponseEntity<ApiResponse<EmploiTempsDTO>> getByTeacherAndType(
            @PathVariable Long teacherId,
            @PathVariable TypeEmploi type) {
        return ok(service.getByTeacherAndType(teacherId, type));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmploiTempsDTO>> create(
            @Valid @RequestBody CreateEmploiTempsRequest request) {
        EmploiTempsDTO created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Emploi créé avec succès", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmploiTempsDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateEmploiTempsRequest request) {
        return ok("Emploi mis à jour", service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ok("Emploi supprimé", null);
    }

    /* ══════════════════════════════════════════════════════════════
       PDF
    ══════════════════════════════════════════════════════════════ */

    /** Télécharger l'emploi d'un teacher précis */
    @GetMapping("/download/{teacherId}/{type}")
    public ResponseEntity<byte[]> downloadPdf(
            @PathVariable Long teacherId,
            @PathVariable TypeEmploi type) {
        byte[] pdf = service.generatePdf(teacherId, type);
        return pdfResponse(pdf, "emploi_" + teacherId + "_" + type.name().toLowerCase() + ".pdf");
    }

    /** Exporter tous les emplois d'un type en un seul PDF */
    @GetMapping("/export/{type}")
    public ResponseEntity<byte[]> exportAll(@PathVariable TypeEmploi type) {
        byte[] pdf = service.generateAllPdf(type);
        return pdfResponse(pdf, "emplois_" + type.name().toLowerCase() + ".pdf");
    }

    /* ── helpers privés ───────────────────────────────────────────── */

    private <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    private <T> ResponseEntity<ApiResponse<T>> ok(String msg, T data) {
        return ResponseEntity.ok(ApiResponse.ok(msg, data));
    }

    private ResponseEntity<byte[]> pdfResponse(byte[] pdf, String filename) {
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_PDF);
        h.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
        h.setContentLength(pdf.length);
        return ResponseEntity.ok().headers(h).body(pdf);
    }
}