<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="application" var="bundle" scope="session"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">
    <link rel="icon" href="<fmt:message key="context.path" bundle="${bundle}" />/assets/images/logo-mh360.png">
    <title>Patient Registration</title>
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700">
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/app.css">
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/registration.css">
    <script>
        var CONTEXT_URL = "<fmt:message key="context.path" bundle="${bundle}" />";
        // Firebase Events
        const FIREBASE_EVENTS_APIKEY = "<fmt:message key="firebase.events.apiKey" bundle="${bundle}" />";
        const FIREBASE_EVENTS_AUTHDOMAIN = "<fmt:message key="firebase.events.authDomain" bundle="${bundle}" />";
        const FIREBASE_EVENTS_PROJECTID = "<fmt:message key="firebase.events.projectId" bundle="${bundle}" />";
        const FIREBASE_EVENTS_STORAGEBUCKET = "<fmt:message key="firebase.events.storageBucket" bundle="${bundle}" />";
        const FIREBASE_EVENTS_MESSAGINGSENDERID = "<fmt:message key="firebase.events.messagingSenderId" bundle="${bundle}" />";
        const FIREBASE_EVENTS_APPID = "<fmt:message key="firebase.events.appId" bundle="${bundle}" />";
        const FIREBASE_EVENTS_MEASUREMENTID = "<fmt:message key="firebase.events.measurementId" bundle="${bundle}" />";
        const FIREBASE_EVENTS_DATABASEURL = "<fmt:message key="firebase.events.databaseURL" bundle="${bundle}" />";
         // Moengage Events
        const MOENGAGE_EVENTS_APPID = "<fmt:message key="moengage.events.appId" bundle="${bundle}" />";
        const MOENGAGE_EVENTS_DEBUG = "<fmt:message key="moengage.events.debug" bundle="${bundle}" />";
    </script>

    <%--<c:if test="${sessionScope.pg_mobile_verified == null || sessionScope.pg_mobile_verified == false}">--%>
    <%--    <c:redirect url="/" />--%>
    <%--</c:if>--%>
    <style>
        .uss-tel .iti__arrow {
            background-image: url('<fmt:message key="context.path" bundle="${bundle}" />/assets/images/caret-tel.svg');
        }
    </style>

</head>
<body>
<div class="d-flex layout-box" id="signUpV2">
    <div class="loader" id="cover-spin" v-if="isLoading === true">
        <div class="circle text-center">
            <img src="assets/images/loader.gif">
        </div>
    </div>
    <div class="col mx-auto">
        <div class="d-flex justify-content-center mb-2 mt-4 p-2">
            <span class="m-0 fs-5 text-header-label color-black"><img src="assets/images/logo-mh360.png" class="img-mh360" id="prp-img-title"></span>
        </div>
        <div class="card border-0">
            <div class="card-body">
                <div class="text-center mb-4 px-2">
                    <h3 class="text-28px text-grey-dark mb-3" id="prp-text-title">Create an account</h3>
                    <span class="text-small font-size-0-9 text-secondary-dark" id="prp-text-title-description">Healthcare is a journey. Start here.</span>
                </div>
                <div class="d-flex flex-row justify-content-center align-items-center mb-4">
                    <button type="button" class="btn btn-primary btn-with-email col-9" id="prp-btn-signup-email">
                        <a href="#"
                           :class="[{'disabled-link': isLoading}, 'text-decoration-none']"
                           @click="signUp('manually')" class="text-white text-16px" id="prp-link-signup-email">Sign Up with an Email
                        </a>
                    </button>
                </div>
                <div class="d-flex flex-row justify-content-center align-items-center mb-4 col-12" id="prp-wapper-divider-or">
                    <span class="m-0 fs-5 text-header-label color-black"><img id="prp-or-image" src="assets/images/or.png"></span>
                </div>
                <div class="d-flex flex-row justify-content-center align-items-center mb-2">
                    <button type="button" class="btn btn-outline-dark col-9 " @click="signUp('myInfo')" :disabled="isLoading" id="prp-btn-signup-signpass"><span class="text-16px" id="prp-text-signup-signpass">Sign Up with</span>
                        <img src="assets/images/singpass.svg" width="100" class="img-signpass" id="prp-img-signup-signpass"/>
                    </button>
                </div>
                <div class="text-center" id="prp-wrapper-description-secure">
                    <div class="text-15px text-secondary-dark">Easy and secure access,<br>
                        faster and hassle-free signup
                    </div>
                </div>
                <div class="fixed-bottom">
                    <div class="text-center">
                        <div class="text-secondary-dark text-15px" id="prp-wrapper-loginnow">Already have an account ? <span><a @click.prevent="gotoLogin" class="dh-link text-15px" id="prp-link-loginnow">Login now</a></span></div>
                    </div>
                    <div class="d-grid col-10 mt-3 mb-4 mx-auto">
                        <a href="#" @click="goToWhatsapp" role="button" class="btn btn-outline-wa" id="prp-login-chat-wa"><img id="prp-wa-image" src="assets/images/whatsapp.svg" class="img-wa"/><span class="text-13px">  Need assistance? Chat with us!</span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
<script type="module" src="<fmt:message key="context.path" bundle="${bundle}" />/js/signUp.js?deploy=<fmt:message key="static.suffix" bundle="${bundle}" />"></script>
</body>

</html>