package com.example.geocare.Schedule;

import java.io.Serializable;

public class ProductItem implements Serializable {
    String routineProductucUse;
    String routineAmount;
    String routineDetailDes;
    String routineName;
    String routineSmallDes;
    int imageIDResource;

    public ProductItem() {}

    public ProductItem(int imageIDResource, String routineProductucUse, String routineAmount, String routineDetailDes, String routineName, String routineSmallDes) {
        this.imageIDResource = imageIDResource;
        this.routineProductucUse = routineProductucUse;
        this.routineAmount = routineAmount;
        this.routineDetailDes = routineDetailDes;
        this.routineName = routineName;
        this.routineSmallDes = routineSmallDes;
    }

    public String getRoutineProductucUse() {
        return routineProductucUse;
    }

    public String getRoutineAmount() {
        return routineAmount;
    }

    public String getRoutineDetailDes() {
        return routineDetailDes;
    }

    public String getRoutineName() {
        return routineName;
    }

    public String getRoutineSmallDes() {
        return routineSmallDes;
    }

    public int getImageIDResource() {
        return imageIDResource;
    }

    public void setRoutineProductucUse(String routineProductucUse) {
        this.routineProductucUse = routineProductucUse;
    }

    public void setRoutineAmount(String routineAmount) {
        this.routineAmount = routineAmount;
    }

    public void setRoutineDetailDes(String routineDetailDes) {
        this.routineDetailDes = routineDetailDes;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public void setRoutineSmallDes(String routineSmallDes) {
        this.routineSmallDes = routineSmallDes;
    }

    public void setImageIDResource(int imageIDResource) {
        this.imageIDResource = imageIDResource;
    }
}
