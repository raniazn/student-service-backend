package org.example.teachingservice.service;

import org.example.teachingservice.dto.CreateEmploiTempsRequest;
import org.example.teachingservice.dto.EmploiTempsDTO;
import org.example.teachingservice.dto.TeacherDTO;
import org.example.teachingservice.entity.TypeEmploi;

import java.util.List;

public interface EmploiTempsService {

    /* ── CRUD ──────────────────────────────────────────────────────── */
    EmploiTempsDTO create(CreateEmploiTempsRequest request);
    EmploiTempsDTO getById(Long id);
    EmploiTempsDTO getByTeacherAndType(Long teacherId, TypeEmploi type);
    List<EmploiTempsDTO> getByType(TypeEmploi type);
    EmploiTempsDTO update(Long id, CreateEmploiTempsRequest request);
    void delete(Long id);

    /* ── Teachers filtrés par type d'emploi (onglets Angular) ───────────────── */
    List<TeacherDTO> getTeachersBySemestre1();
    List<TeacherDTO> getTeachersBySemestre2();
    List<TeacherDTO> getTeachersByFormationJour();
    List<TeacherDTO> getTeachersByFormationSoir();
    List<TeacherDTO> getTeachersByWeekEnd();

    /* ── PDF ────────────────────────────────────────────────────────── */
    byte[] generatePdf(Long teacherId, TypeEmploi type);
    byte[] generateAllPdf(TypeEmploi type);
}