package com.distributed.dbproxy.sql.basic.appcloud_front;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Idan on 2018/1/15.
 */
@Entity
@Table(name="appkey")
@Data
public class AppkeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Basic
    @Column(name="user_id")
    private Integer userId;

    @Basic
    @Column(name="user_email")
    private String userEmail;

    @Basic
    @Column(name="appkey_id")
    private String appkeyId;

    @Basic
    @Column(name="appkey_secret")
    private String appkeySecret;

    @Basic
    @Column(name="provider")
    private String provider;

    @Basic
    @Column(name="appname")
    private String appname;

    @Basic
    @Column
    private String  region;

    @Basic
    @Column
    private String  zone;

    @Basic
    @Column(name = "appcloud_userEmail")
    private String  appcloudUserEmail;

    @Basic
    @Column
    private Integer  state;


}
