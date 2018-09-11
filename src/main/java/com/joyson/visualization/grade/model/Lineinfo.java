package com.joyson.visualization.grade.model;

import java.util.Date;
import javax.persistence.*;

public class Lineinfo {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DeptNo")
    private String deptno;

    @Column(name = "DeptName")
    private String deptname;

    @Column(name = "LineAddress")
    private String lineaddress;

    @Column(name = "OperUserID")
    private Integer operuserid;

    @Column(name = "OperDate")
    private Date operdate;

    @Column(name = "Del")
    private Boolean del;

    @Column(name = "ShiftALeader")
    private String shiftaleader;

    @Column(name = "ShiftBLeader")
    private String shiftbleader;

    /**
     * @return Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return DeptNo
     */
    public String getDeptno() {
        return deptno;
    }

    /**
     * @param deptno
     */
    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    /**
     * @return DeptName
     */
    public String getDeptname() {
        return deptname;
    }

    /**
     * @param deptname
     */
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    /**
     * @return LineAddress
     */
    public String getLineaddress() {
        return lineaddress;
    }

    /**
     * @param lineaddress
     */
    public void setLineaddress(String lineaddress) {
        this.lineaddress = lineaddress;
    }

    /**
     * @return OperUserID
     */
    public Integer getOperuserid() {
        return operuserid;
    }

    /**
     * @param operuserid
     */
    public void setOperuserid(Integer operuserid) {
        this.operuserid = operuserid;
    }

    /**
     * @return OperDate
     */
    public Date getOperdate() {
        return operdate;
    }

    /**
     * @param operdate
     */
    public void setOperdate(Date operdate) {
        this.operdate = operdate;
    }

    /**
     * @return Del
     */
    public Boolean getDel() {
        return del;
    }

    /**
     * @param del
     */
    public void setDel(Boolean del) {
        this.del = del;
    }

    /**
     * @return ShiftALeader
     */
    public String getShiftaleader() {
        return shiftaleader;
    }

    /**
     * @param shiftaleader
     */
    public void setShiftaleader(String shiftaleader) {
        this.shiftaleader = shiftaleader;
    }

    /**
     * @return ShiftBLeader
     */
    public String getShiftbleader() {
        return shiftbleader;
    }

    /**
     * @param shiftbleader
     */
    public void setShiftbleader(String shiftbleader) {
        this.shiftbleader = shiftbleader;
    }
}