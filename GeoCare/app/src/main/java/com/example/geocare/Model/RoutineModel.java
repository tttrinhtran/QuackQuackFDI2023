package com.example.geocare.Model;

public class RoutineModel {

    String RoutineName;
    String RoutineId;
    String RoutineProductucUse;
    String RoutineSmallDes;
    String RoutineDetailDes;
    String RoutineAmount;
    String skinType;
    String weatherType;
    public RoutineModel() {
    }

    public RoutineModel(String routineName, String routineId, String routineProductucUse, String routineSmallDes, String routineDetailDes, String routineAmount, String skinType, String weatherType) {
        RoutineName = routineName;
        RoutineId = routineId;
        RoutineProductucUse = routineProductucUse;
        RoutineSmallDes = routineSmallDes;
        RoutineDetailDes = routineDetailDes;
        RoutineAmount = routineAmount;
        this.skinType = skinType;
        this.weatherType = weatherType;
    }

    @Override
    public String toString() {
        return "RoutineModel{" +
                "RoutineName='" + RoutineName + '\'' +
                ", RoutineId='" + RoutineId + '\'' +
                ", RoutineProductucUse='" + RoutineProductucUse + '\'' +
                ", RoutineSmallDes='" + RoutineSmallDes + '\'' +
                ", RoutineDetailDes='" + RoutineDetailDes + '\'' +
                ", RoutineAmount='" + RoutineAmount + '\'' +
                ", skinType='" + skinType + '\'' +
                ", weatherType='" + weatherType + '\'' +
                '}';
    }

    public String getRoutineName() {
        return RoutineName;
    }

    public void setRoutineName(String routineName) {
        RoutineName = routineName;
    }

    public String getRoutineId() {
        return RoutineId;
    }

    public void setRoutineId(String routineId) {
        RoutineId = routineId;
    }

    public String getRoutineProductucUse() {
        return RoutineProductucUse;
    }

    public void setRoutineProductucUse(String routineProductucUse) {
        RoutineProductucUse = routineProductucUse;
    }

    public String getRoutineSmallDes() {
        return RoutineSmallDes;
    }

    public void setRoutineSmallDes(String routineSmallDes) {
        RoutineSmallDes = routineSmallDes;
    }

    public String getRoutineDetailDes() {
        return RoutineDetailDes;
    }

    public void setRoutineDetailDes(String routineDetailDes) {
        RoutineDetailDes = routineDetailDes;
    }

    public String getRoutineAmount() {
        return RoutineAmount;
    }

    public void setRoutineAmount(String routineAmount) {
        RoutineAmount = routineAmount;
    }

    public String getSkinType() {
        return skinType;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }
}
