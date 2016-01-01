package com.birdlabs.mhrd.util;

/**
 * Contains all the links
 * Created by bijoy on 10/24/15.
 */
public class Api {

    public static String getSlashLessBaseLink() {
        return "https://mhrdlink.thecodershub.com";
    }

    public static String getBaseLink() {
        return getSlashLessBaseLink() + "/api/";
    }

    public static String getAccountBaseLink() {
        return getBaseLink() + "account/";
    }

    public static String getCollegeBaseLink() {
        return getBaseLink() + "college/";
    }

    public static String getTagBaseLink() {
        return getBaseLink() + "tag/";
    }

    public static String getUserBaseLink() {
        return getBaseLink() + "user/";
    }

    public static String getPostBaseLink() {
        return getBaseLink() + "post/";
    }

    public static String getRepliesBaseLink() {
        return getBaseLink() + "reply/";
    }

    public static String getRegisterLink() {
        return getAccountBaseLink() + "register/";
    }

    public static String getLoginLink() {
        return getAccountBaseLink() + "login/";
    }

    public static String getSignupLink() {
        return getAccountBaseLink() + "create_account/";
    }

    public static String getResendCodeLink() {
        return getAccountBaseLink() + "resend/";
    }

    public static String getCollegeLink() {
        return getCollegeBaseLink();
    }

    public static String getCollegeLink(Integer id) {
        return getCollegeBaseLink() + id + "/";
    }

    public static String getUpdateProfileLink(Integer id) {
        return getUserBaseLink() + id + "/update_profile/";
    }

    public static String getUpdatePictureLink(Integer id) {
        return getUserBaseLink() + id + "/update_picture/";
    }

    public static String getDesignationLink(Integer id) {
        return getUserBaseLink() + id + "/get_designation/";
    }

    public static String getUpdateDesignationLink(Integer id) {
        return getUserBaseLink() + id + "/add_designation/";
    }

    public static String getAllUserProfile() {
        return getUserBaseLink();
    }

    public static String getCurrentUserProfile() {
        return getUserBaseLink() + "current/";
    }

    public static String getUserProfile(Integer id) {
        return getUserBaseLink() + id + "/";
    }

    public static String getAllTags() {
        return getTagBaseLink();
    }

    public static String getSendPostLink() {
        return getPostBaseLink();
    }

    public static String getFilteredPostLink() {
        return getPostBaseLink();// + "filtered/";
    }

    public static String getAllPostLink() {
        return getPostBaseLink();
    }

    public static String getPostLink(Integer id) {
        return getPostBaseLink() + id + "/";
    }

    public static String getRepliesLink(Integer id) {
        return getPostLink(id) + "get_replies/";
    }

    public static String getUpvoteLink(Integer id) {
        return getPostLink(id) + "upvote/";
    }

    public static String getRemoveVoteLink(Integer id) {
        return getPostLink(id) + "remove_vote/";
    }

    public static String getDownvoteLink(Integer id) {
        return getPostLink(id) + "downvote/";
    }

    public static String getAddRepliesLink() {
        return getRepliesBaseLink() + "add/";
    }

    public static String getDeleteRepliesLink(Integer id) {
        return getRepliesBaseLink() + id + "/delete/";
    }

    public static String getUpvoteRepliesLink(Integer id) {
        return getRepliesBaseLink() + id + "/upvote/";
    }

    public static String getDownvoteRepliesLink(Integer id) {
        return getRepliesBaseLink() + id + "/downvote/";
    }


}
