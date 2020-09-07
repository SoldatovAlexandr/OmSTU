package com.example.omstugradebook.dto;

public class DtoValidator {
    public void validate(ScheduleDtoResponse subjectDtoResponse) throws DtoResponseException {
        validateDayOfWeek(subjectDtoResponse.getDayOfWeek());
        validateBeginLesson(subjectDtoResponse.getBeginLesson());
        validateDate(subjectDtoResponse.getDate());
        validateBuilding(subjectDtoResponse.getBuilding());
        validateDiscipline(subjectDtoResponse.getDiscipline());
        validateEndLesson(subjectDtoResponse.getEndLesson());
        validateKindOfWork(subjectDtoResponse.getKindOfWork());
        validateLecturer(subjectDtoResponse.getLecturer());
    }

    private void validateBeginLesson(String beginLesson) throws DtoResponseException {
        if (beginLesson == null) {
            throw new DtoResponseException();
        }
    }

    private void validateBuilding(String building) throws DtoResponseException {
        if (building == null) {
            throw new DtoResponseException();
        }
    }

    private void validateDate(String date) throws DtoResponseException {
        if (date == null) {
            throw new DtoResponseException();
        }
    }

    private void validateDayOfWeek(int dayOfWeek) throws DtoResponseException {
        if (dayOfWeek > 7 || dayOfWeek < 1) {
            throw new DtoResponseException();
        }
    }

    private void validateDiscipline(String discipline) throws DtoResponseException {
        if (discipline == null) {
            throw new DtoResponseException();
        }
    }

    private void validateEndLesson(String endLesson) throws DtoResponseException {
        if (endLesson == null) {
            throw new DtoResponseException();
        }
    }

    private void validateKindOfWork(String kindOfWork) throws DtoResponseException {
        if (kindOfWork == null) {
            throw new DtoResponseException();
        }
    }

    private void validateLecturer(String lecturer) throws DtoResponseException {
        if (lecturer == null) {
            throw new DtoResponseException();
        }
    }

}
