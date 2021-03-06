package com.example.eventplanner.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    public Long cid;

    @Column(name = "coursename")
    String cname;

    @Column(name = "courseduration")
    String cduration;

    @Column(name = "Startingdate")
    String csdate;

    public String cflag;

    @Column(name = "briefing")
    String cbrief;

    @Column(name = "details")
    String cdetail;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Course(String cflag) {
        this.cflag = cflag;
    }

    public Course(Long cid, String cname, String cduration, String csdate, String cflag, String cbrief, String cdetail) {
        this.cid = cid;
        this.cname = cname;
        this.cduration = cduration;
        this.csdate = csdate;
        this.cflag = cflag;
        this.cbrief = cbrief;
        this.cdetail = cdetail;
    }

    public String getCflag() {
        return cflag;
    }

    public void setCflag(String cflag) {
        this.cflag = cflag;
    }

    public String getCname() {
        return cname;
    }

    public String getCbrief() {
        return cbrief;
    }

    public void setCbrief(String cbrief) {
        this.cbrief = cbrief;
    }

    public String getCdetail() {
        return cdetail;
    }

    public void setCdetail(String cdetail) {
        this.cdetail = cdetail;
    }

    public Course() {

    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCname(Course course) {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCduration() {
        return cduration;
    }

    public void setCduration(String cduration) {
        this.cduration = cduration;
    }

    public String getCsdate() {
        return csdate;
    }

    public void setCsdate(String csdate) {
        this.csdate = csdate;
    }
}