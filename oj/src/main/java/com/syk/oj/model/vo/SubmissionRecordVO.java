package com.syk.oj.model.vo;

public class SubmissionRecordVO {
    private String nickname;

    private String title;

    private Long current;

    private Long size;

    public SubmissionRecordVO(String nickname, String title, Long current, Long size) {
        this.nickname = nickname;
        this.title = title;
        this.current = current;
        this.size = size;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
