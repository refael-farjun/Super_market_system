package Employees.BuisnessLayer;
import Employees.DataAccessLayer.DAOs.ShiftDAO;
import Employees.DataAccessLayer.DTOs.ShiftDTO;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.Date;

public class EvningShift extends Shift {

    EvningShift(LocalDate _date, ShiftDAO dao) {
        super(_date, dao);
        start = LocalTime.of(14,0);
        end = LocalTime.of(22,0);
    }

    @Override
    public EvningShift clone() {
        EvningShift cloned = new EvningShift(date, new ShiftDAO());
        cloned.start = start;
        cloned.end = end;
        return cloned;
    }
}
