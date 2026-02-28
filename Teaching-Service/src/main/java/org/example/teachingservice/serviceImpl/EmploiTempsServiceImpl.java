package org.example.teachingservice.serviceImpl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teachingservice.dto.CreateEmploiTempsRequest;
import org.example.teachingservice.dto.CreateSeanceRequest;
import org.example.teachingservice.dto.EmploiTempsDTO;
import org.example.teachingservice.dto.TeacherDTO;
import org.example.teachingservice.entity.*;
import org.example.teachingservice.exception.DuplicateResourceException;
import org.example.teachingservice.exception.ResourceNotFoundException;
import org.example.teachingservice.mapper.EmploiTempsMapper;
import org.example.teachingservice.repository.EmploiTempsRepository;
import org.example.teachingservice.repository.TeacherRepository;
import org.example.teachingservice.service.EmploiTempsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmploiTempsServiceImpl implements EmploiTempsService {

    private final EmploiTempsRepository emploiRepo;
    private final TeacherRepository teacherRepo;
    private final EmploiTempsMapper mapper;
    private final PdfService pdfService;

    /* ── CREATE ───────────────────────────────────────────────────── */
    @Override
    @Transactional
    public EmploiTempsDTO create(CreateEmploiTempsRequest req) {

        Teacher teacher = teacherRepo.findById(req.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", req.getTeacherId()));

        if (emploiRepo.existsByTeacherIdAndTypeEmploi(req.getTeacherId(), req.getTypeEmploi())) {
            throw new DuplicateResourceException(
                    "Ce teacher a déjà un emploi de type " + req.getTypeEmploi());
        }

        EmploiTemps emploi = EmploiTemps.builder()
                .teacher(teacher)
                .typeEmploi(req.getTypeEmploi())
                .anneeUniversitaire(req.getAnneeUniversitaire())
                .semestre(req.getSemestre())
                .seances(new ArrayList<>())
                .build();

        if (req.getSeances() != null) {
            for (CreateSeanceRequest sr : req.getSeances()) {
                emploi.getSeances().add(Seance.builder()
                        .emploiTemps(emploi)
                        .jour(sr.getJour())
                        .heureDebut(sr.getHeureDebut())
                        .heureFin(sr.getHeureFin())
                        .matiere(sr.getMatiere())
                        .typeSeance(sr.getTypeSeance())
                        .salle(sr.getSalle())
                        .groupe(sr.getGroupe())
                        .niveau(sr.getNiveau())
                        .build());
            }
        }

        EmploiTemps saved = emploiRepo.save(emploi);
        log.info("EmploiTemps créé ID={}", saved.getId());

        return mapper.toEmploiTempsDTO(saved);
    }

    /* ── READ ─────────────────────────────────────────────────────── */

    @Override
    public EmploiTempsDTO getById(Long id) {
        return mapper.toEmploiTempsDTO(
                emploiRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("EmploiTemps", id)));
    }

    @Override
    public EmploiTempsDTO getByTeacherAndType(Long teacherId, TypeEmploi type) {
        return mapper.toEmploiTempsDTO(
                emploiRepo.findWithSeancesByTeacherIdAndType(teacherId, type)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Emploi " + type + " introuvable pour teacher ID " + teacherId)));
    }

    /* ✅ CORRECTION LazyInitializationException */
    @Override
    @Transactional(readOnly = true)
    public List<EmploiTempsDTO> getByType(TypeEmploi type) {

        List<EmploiTemps> list =
                emploiRepo.findByTypeWithTeacher(type);

        return mapper.toEmploiTempsListDTO(list);
    }

    /* ── Teachers par type ────────────────────────────────────────── */

    @Override
    public List<TeacherDTO> getTeachersBySemestre1() {
        return mapper.toTeacherDTOList(emploiRepo.findTeachersBySemestre1());
    }

    @Override
    public List<TeacherDTO> getTeachersBySemestre2() {
        return mapper.toTeacherDTOList(emploiRepo.findTeachersBySemestre2());
    }

    @Override
    public List<TeacherDTO> getTeachersByFormationJour() {
        return mapper.toTeacherDTOList(emploiRepo.findTeachersByFormationJour());
    }

    @Override
    public List<TeacherDTO> getTeachersByFormationSoir() {
        return mapper.toTeacherDTOList(emploiRepo.findTeachersByFormationSoir());
    }

    @Override
    public List<TeacherDTO> getTeachersByWeekEnd() {
        return mapper.toTeacherDTOList(emploiRepo.findTeachersByWeekEnd());
    }

    /* ── UPDATE ───────────────────────────────────────────────────── */

    @Override
    @Transactional
    public EmploiTempsDTO update(Long id, CreateEmploiTempsRequest req) {

        EmploiTemps emploi = emploiRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmploiTemps", id));

        emploi.setAnneeUniversitaire(req.getAnneeUniversitaire());
        emploi.setSemestre(req.getSemestre());
        emploi.getSeances().clear();

        if (req.getSeances() != null) {
            for (CreateSeanceRequest sr : req.getSeances()) {
                emploi.getSeances().add(Seance.builder()
                        .emploiTemps(emploi)
                        .jour(sr.getJour())
                        .heureDebut(sr.getHeureDebut())
                        .heureFin(sr.getHeureFin())
                        .matiere(sr.getMatiere())
                        .typeSeance(sr.getTypeSeance())
                        .salle(sr.getSalle())
                        .groupe(sr.getGroupe())
                        .niveau(sr.getNiveau())
                        .build());
            }
        }

        return mapper.toEmploiTempsDTO(emploiRepo.save(emploi));
    }

    /* ── DELETE ───────────────────────────────────────────────────── */

    @Override
    @Transactional
    public void delete(Long id) {

        if (!emploiRepo.existsById(id)) {
            throw new ResourceNotFoundException("EmploiTemps", id);
        }

        emploiRepo.deleteById(id);
        log.info("EmploiTemps supprimé ID={}", id);
    }

    /* ── PDF ──────────────────────────────────────────────────────── */

    @Override
    public byte[] generatePdf(Long teacherId, TypeEmploi type) {

        EmploiTemps et = emploiRepo.findWithSeancesByTeacherIdAndType(teacherId, type)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Emploi " + type + " introuvable pour teacher ID " + teacherId));

        return pdfService.generateEmploiPdf(et);
    }

    @Override
    public byte[] generateAllPdf(TypeEmploi type) {

        List<EmploiTemps> list = emploiRepo.findAllWithSeancesByType(type);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Aucun emploi de type " + type);
        }

        return pdfService.generateAllEmploiPdf(list);
    }
}