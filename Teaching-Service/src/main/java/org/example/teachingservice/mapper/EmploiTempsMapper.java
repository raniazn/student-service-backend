package org.example.teachingservice.mapper;

import org.example.teachingservice.dto.EmploiTempsDTO;
import org.example.teachingservice.dto.SeanceDTO;
import org.example.teachingservice.dto.TeacherDTO;
import org.example.teachingservice.entity.EmploiTemps;
import org.example.teachingservice.entity.Seance;
import org.example.teachingservice.entity.Teacher;
import org.mapstruct.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmploiTempsMapper {

    /* ── Teacher → TeacherDTO ─────────────────────────────────────── */
    @Mapping(target = "hasEmploi", expression = "java(false)")
    TeacherDTO toTeacherDTO(Teacher teacher);

    List<TeacherDTO> toTeacherDTOList(List<Teacher> teachers);

    /* ── EmploiTemps → EmploiTempsDTO ─────────────────────────────── */
    @Mapping(target = "teacherId",        source = "teacher.id")
    @Mapping(target = "teacherLastName",  source = "teacher.lastName")
    @Mapping(target = "teacherFirstName", source = "teacher.firstName")
    @Mapping(target = "teacherMatricule", source = "teacher.matricule")
    @Mapping(target = "teacherIdCard",    source = "teacher.idCard")
    EmploiTempsDTO toEmploiTempsDTO(EmploiTemps emploiTemps);

    List<EmploiTempsDTO> toEmploiTempsListDTO(List<EmploiTemps> list);

    /* ── Seance → SeanceDTO ───────────────────────────────────────── */
    @Mapping(target = "jour",       expression = "java(seance.getJour().name())")
    @Mapping(target = "heureDebut", expression = "java(formatTime(seance.getHeureDebut()))")
    @Mapping(target = "heureFin",   expression = "java(formatTime(seance.getHeureFin()))")
    SeanceDTO toSeanceDTO(Seance seance);

    List<SeanceDTO> toSeanceDTOList(List<Seance> seances);

    default String formatTime(LocalTime time) {
        if (time == null) return null;
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}