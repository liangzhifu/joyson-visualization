package com.joyson.visualization.grade.model;

import java.util.Date;
import javax.persistence.*;

public class Userinfo {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UserNo")
    private String userno;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Department")
    private String department;

    @Column(name = "CardNo")
    private String cardno;

    @Column(name = "Email")
    private String email;

    @Column(name = "DeptID")
    private Integer deptid;

    @Column(name = "ShiftName")
    private String shiftname;

    @Column(name = "Luncurr")
    private String luncurr;

    @Column(name = "Access")
    private String access;

    @Column(name = "Password")
    private String password;

    @Column(name = "OperUserID")
    private Integer operuserid;

    @Column(name = "OperDate")
    private Date operdate;

    @Column(name = "Del")
    private Boolean del;

    @Column(name = "InductionTime")
    private Date inductiontime;

    @Column(name = "Source")
    private String source;

    @Column(name = "Photo")
    private byte[] photo;

    /**
     * @return ID
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
     * @return UserNo
     */
    public String getUserno() {
        return userno;
    }

    /**
     * @param userno
     */
    public void setUserno(String userno) {
        this.userno = userno;
    }

    /**
     * @return UserName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return CardNo
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @param cardno
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    /**
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return DeptID
     */
    public Integer getDeptid() {
        return deptid;
    }

    /**
     * @param deptid
     */
    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    /**
     * @return ShiftName
     */
    public String getShiftname() {
        return shiftname;
    }

    /**
     * @param shiftname
     */
    public void setShiftname(String shiftname) {
        this.shiftname = shiftname;
    }

    /**
     * @return Luncurr
     */
    public String getLuncurr() {
        return luncurr;
    }

    /**
     * @param luncurr
     */
    public void setLuncurr(String luncurr) {
        this.luncurr = luncurr;
    }

    /**
     * @return Access
     */
    public String getAccess() {
        return access;
    }

    /**
     * @param access
     */
    public void setAccess(String access) {
        this.access = access;
    }

    /**
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return InductionTime
     */
    public Date getInductiontime() {
        return inductiontime;
    }

    /**
     * @param inductiontime
     */
    public void setInductiontime(Date inductiontime) {
        this.inductiontime = inductiontime;
    }

    /**
     * @return Source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return Photo
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * @param photo
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}