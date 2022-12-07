<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="application" var="bundle" scope="session" />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">
    <link rel="icon" href="<fmt:message key="context.path" bundle="${bundle}"/>/assets/images/logo-mh360.png">
    <title>Patient Registration</title>
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700">
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/countrySelect.min.css">
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/bs-stepper.min.css">
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/gijgo.min.css">
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/daterangepicker.css">
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/app.css">
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/registration.css">
 
    <script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/intl-tel-input/intlTelInput.js"></script>
    <link rel="stylesheet" href="<fmt:message key="context.path" bundle="${bundle}" />/css/vendor/intlTelInput.css">

    <script>
        var CONTEXT_URL = "<fmt:message key="context.path" bundle="${bundle}" />";
        var OTP_VALIDITY = "<fmt:message key="otp.resend.interval" bundle="${bundle}" />";
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
        // Recaptcha  
        const RECAPTCHA_SITE_KEY = "<fmt:message key="recaptcha.site-key" bundle="${bundle}" />";
        var verifyCallback = function(response) { document.getElementById("token").value = response; window.vuePRP.triggerToken()};
        var onloadCallback = function() {grecaptcha.render('recaptcha-element', {'sitekey' : RECAPTCHA_SITE_KEY, 'callback' : verifyCallback})};
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
<div class="layout" id="register">
    <div class="loader" id="cover-spin" v-if="isLoading === true">
        <div class="circle text-center">
            <img id="prp-img-loader" src="assets/images/loader.gif">
        </div>
    </div>
    <div class="modal fade" id="modal-success" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true"
         data-bs-backdrop="static" data-bs-keyboard="false">
        <div class="modal-dialog modal-dialog-mh modal-dialog-centered mx-auto ">
            <div class="modal-content modal-content-mh">
                <div class="modal-header modal-header-mh">
                </div>
                <div class="modal-body text-center pb-5">
                    <img id="prp-img-icon-success" src="assets/images/success.svg" width="50" class="mx-auto"/>
                    <div id="prp-label-notif-success-container" class="mt-2">
                        <div id="prp-label-notif-success" class="fw-bold">Success!</div>
                        Thank you
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="modal-error" tabindex="-1" aria-labelledby="modalLabel" aria-hidden="true"
         data-bs-backdrop="static" data-bs-keyboard="false">
        <div class="modal-dialog modal-dialog-mh modal-dialog-centered mx-auto ">
            <div class="modal-content modal-content-mh">
                <div class="modal-header modal-header-mh">
                    <button id="prp-btn-close-modal" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body text-center pb-5">
                    <div id="prp-label-error-notif" class="mt-2">
                        Error occured
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layout-box">
        <div class="col mx-auto">
            <div class="d-flex justify-content-center mb-2 mt-2 p-2">
                <span class="m-0 fs-5 text-header-label color-black"><img id="logo-mh360" src="assets/images/logo-mh360.png" class="img-mh360"></span>
            </div>
            <div class="card border-0 px-3">
                <div class="card-body">
                    <div class="d-flex justify-content-center">
                        <div class="container container-dh mx-auto">
                            <div class="bs-stepper" id="stepper1">
                                <div class="bs-stepper-header container-stepper" role="tablist">
                                    <div class="step position-relative"
                                         v-bind:class="{ active : (dataStep === 1 ), 'step-completed' : (dataStep === 2 || dataStep === 3)  }"
                                         data-index="1" data-target="#account_info_part">
                                        <a class="step-trigger d-block mx-auto pb-0 " role="tab"
                                           aria-controls="account-info-part"
                                           id="account-info-part-trigger" aria-selected="true">
                                            <span class="bs-stepper-circle"></span>
                                        </a>
                                    </div>
                                    <div class="line" v-bind:class="{ activeLine : dataStep === 2 || dataStep === 3 }"
                                         data-index="1"></div>
                                    <div class="step position-relative"
                                         v-bind:class="{ active : dataStep === 2,  'step-completed' : (dataStep === 3) }"
                                         data-index="2" data-target="#personal-info-part">
                                        <a class="step-trigger d-block mx-auto pb-0" role="tab"
                                           aria-controls="personal-info-part"
                                           id="personal-info-part-trigger">
                                            <span class="bs-stepper-circle"></span>
                                        </a>
                                    </div>
                                    <div class="line" v-bind:class="{ activeLine : dataStep === 3  }"
                                         data-index="2"></div>
                                    <div class="step position-relative" v-bind:class="{ active : dataStep === 3 }"
                                         data-index="3" data-target="#verification-part">
                                        <a class="step-trigger d-block mx-auto pb-0" role="tab"
                                           aria-controls="verification-part"
                                           id="verification-part-trigger">
                                            <span class="bs-stepper-circle"></span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bs-stepper-content mt-2">
                        <!-- your steps content here -->
                        <div id="account_info_part" class="content" role="tabpanel"
                             aria-labelledby="account-info-part-trigger" v-if="dataStep == 1">
                            <div class="d-flex justify-content-center mb-2 mt-2 p-2">
                                <span id="prp-label-title-step1" class="text-28px text-grey-dark">Create My Profile</span>
                            </div>
                            <div v-if="showErrorMessage" class="alert alert-danger alert-dismissible fade alert-error show" role="alert">
                                <img id="error-message-info-user" src="assets/images/fail.svg"/>
                                <span id="error-message-info-user" >{{errorMessage}}</span>
                                <!-- <button id="prp-btn-title-step1-close" type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button> -->
                            </div>
                            <form>
                                <div class="group">
                                    <div class="form-group">
                                        <label for="email" class="text-15px pb-1" id="prp-label-input-email">Email</label>
                                        <input ref="email" type="text" class="form-control" v-model="account_info_part.email"
                                               @paste="(event) => {setTimeout(function(e){event.target.blur();event.target.focus()},1)}"
                                               @keydown.space="(event) => event.preventDefault()"
                                               @change="account_info_part.email = ($event.target.value || '').toLowerCase().trim()"
                                               id="email"
                                               :style="{borderColor: hasInputEmailError() ? 'red !important' : ''}">
                                        <div v-if="account_info_part.email == '' && isSubmitted1 == true"
                                             class="text-small invalid-feedback text-left d-block" id="prp-helper-input-email-required">This field is
                                            required.
                                        </div>
                                        <div v-else-if="!hasEmailCorrect() && isSubmitted1 == true"
                                             class="text-small invalid-feedback text-left d-block" id="prp-helper-input-email-invalid">Please enter valid email address.
                                        </div>
                                        <div v-else-if="isEmailExists  && isSubmitted1 == true"
                                             class="text-small invalid-feedback text-left d-block" id="prp-helper-input-email-exist">This email already exists.
                                        </div>
                                    </div>
                                </div>
                                <div class="group">
                                    <div class="form-group position-relative">
                                        <label for="password" class="text-15px pb-1" id="prp-helper-input-password">Password</label>
                                        <input maxlength="30" v-bind:type="account_info_part.showPassword ? 'text' : 'password'"
                                               class="form-control" v-model="account_info_part.password" id="password"
                                               :style="{borderColor: account_info_part.password == '' && isSubmitted1 == true ? 'red !important' : ''}">
                                        <span class="eye-password">
                                            <img @click="togglePassword"
                                                 v-bind:src="account_info_part.showPassword ? 'assets/images/eye-show.svg' : 'assets/images/eye-hide.svg'"
                                                 class="img-fluid" id="toggle-password" alt="Eye icon">
                                        </span>
                                    </div>
                                    <div v-if="account_info_part.password == '' && isSubmitted1 == true"
                                         class="text-small invalid-feedback text-left d-block" id="prp-helper-input-password-required">This field is required.
                                    </div>

                                </div>
                                <div class="group" v-if="account_info_part.password != ''">
                                    <div class="card bg-message">
                                        <div class="card-body bg-card card-password">
                                            <div class="text-dark-grey">
                                                <p class="text-13px" id="prp-helper-input-password-1">Your password should have: </p>
                                                <ul class="list-unstyled text-13px">
                                                    <li class="mt-1"><img
                                                            v-bind:src="PasswordValidationData.hasNumber ? 'assets/images/circle-on.svg' : 'assets/images/circle-off.svg'" id="prp-img-heloer-password-1"><span
                                                            class="text-small" id="prp-helper-input-password-1"> At least one number</span></li>
                                                    <li class="mt-1"><img
                                                            v-bind:src="PasswordValidationData.hasUppercase && PasswordValidationData.hasLowercase ? 'assets/images/circle-on.svg' : 'assets/images/circle-off.svg'" id="prp-img-heloer-password-2"><span
                                                            class="text-small" id="prp-helper-input-password-2"> At least one uppercase <b>and</b> one lowercase letter</span>
                                                    </li>
                                                    <li class="mt-1"><img
                                                            v-bind:src="PasswordValidationData.hasSpecialChar ? 'assets/images/circle-on.svg' : 'assets/images/circle-off.svg'" id="prp-img-heloer-password-3"><span
                                                            class="text-small" id="prp-helper-input-password-3"> At least one special character</span>
                                                    </li>
                                                    <li class="mt-1"><img
                                                            v-bind:src="PasswordValidationData.has8Chars ? 'assets/images/circle-on.svg' : 'assets/images/circle-off.svg'" id="prp-img-heloer-password-4"><span
                                                            class="text-small" id="prp-helper-input-password-4"> Minimum 8 characters</span></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="group form-check pt-2">
                                    <input class="form-check-input"
                                           v-model="consentMarketing"
                                           type="checkbox" id="flexCheckChecked"> <label id="prp-label-consent"
                                        class="form-check-label text-12px text-secondary-dark-grey" for="flexCheckChecked">I consent to IHH Healthcare, their representatives 
                                        and/or agents and/or related corporations including its affiliates and business partners collecting, using and disclosing my personal 
                                        data for marketing and promotional purposes, including but not limited to that available on or in relation to MyHealth360 mobile app.
                                </label>
                                </div>
                                <div class="group form-check pt-2">
                                    <input class="form-check-input"
                                           v-model="agreeReceiveMarketing"
                                           type="checkbox" id="flexCheckChecked2"> <label id="prp-label-marketing"
                                        class="form-check-label text-12px text-secondary-dark-grey" for="flexCheckChecked2">I agree to receive marketing messages via SMS, 
                                        telephone call and other Singapore phone number-based messaging, regardless of my registration with the Do-Not-Call registry.
                                </label>
                                </div>
                                <div class="group d-grid">
                                    <button v-bind:disabled="PasswordValidationData.hasNumber &&  PasswordValidationData.hasUppercase && PasswordValidationData.hasSpecialChar && PasswordValidationData.has8Chars && account_info_part.email !== '' ? false : true  "
                                            type="button" class="btn btn-primary text-white text-16px"
                                            @click.prevent="goToStep(2)" id="prp-btn-submit-1">Accept and Continue
                                    </button>
                                </div>
                            </form>
                            <div class="group d-grid text-secondary-dark-grey text-12px">
                                <div>
                                    <p align="left" id="prp-paragraph-1">By providing the information set out in this form, I consent to IHH Healthcare and their representatives 
                                    and/or agents and/or related corporations including its affiliates collecting, using and disclosing my personal data to provide me with medical 
                                    and healthcare related services and other reasonably related purposes. Such purposes are set out in the <a target="_blank" href="https://www.ihhhealthcare.com/singapore/MyHealth360-data-protection-notice"
                                        class="dh-link" id="prp-email-protection-link">MyHealth360 Data Privacy Policy</a>.
                                    <span>
                                    
                                    </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="personal_info_part" class="content" role="tabpanel" aria-labelledby="personal-info-trigger"
                         v-if="dataStep == 2">
                        <div class="d-flex justify-content-center mb-2 mt-2 p-2">
                            <span id="prp-label-title-step2" class="text-28px text-grey-dark">Create My Profile</span>
                        </div>
                        <div class="d-flex justify-content-center mb-2 mt-2 p-2">
                            <img src="assets/images/note.svg" class="img-fluid img-register" id="prp-img-title-step2-1">
                            <a data-bs-toggle="modal" data-bs-target="#noteRegister" class="link dh-link-pop-up text-15px position-text" id="prp-label-title-step2-1">
                                 Please provide your first and last name as per your NRIC or Passport.
                            </a>
                        </div>
                        <div v-if="showErrorMessage" class="alert alert-danger alert-dismissible fade alert-error show" role="alert">
                            <img src="assets/images/fail.svg" id="prp-img-title-step2-2"/>
                            <span id="error-message-submit" style="font-size: 13px">{{errorMessage}}</span>
                            <!-- <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="prp-btn-title-step-2-close"></button> -->
                        </div>
                        <form>
                            <div class="group">
                                <div class="form-group">
                                    <label for="firstname" class="text-15px pb-1" id="prp-label-input-firstname">First Name</label>
                                    <input maxlength="128" ref="firstName"  type="text" class="form-control" v-model="personal_info_part.firstName"
                                           id="firstName" :style="{borderColor: hasInputNameError('firstName')  ? 'red !important' : ''}">
                                    <div v-if="personal_info_part.firstName == '' && isSubmitted2 == true"
                                         class="text-small invalid-feedback text-left d-block" id="prp-helper-input-firstname-required">This field is required.
                                    </div>
                                    <div v-if="!isAlpha(personal_info_part.firstName)" class="text-small invalid-feedback text-left d-block"
                                         :style="{display: personal_info_part.firstName === '' ? 'none !important' : 'inline !important'}" id="prp-helper-input-firstname-isalpha">
                                        This field only allows alphabet characters
                                    </div>

                                </div>
                            </div>
                            <div class="group">
                                <div class="form-group">
                                    <label for="lastName" class="text-15px pb-1" id="prp-label-input-lastname">Last Name</label>
                                    <input maxlength="128" type="text" class="form-control" v-model="personal_info_part.lastName"
                                           id="lastName" :style="{borderColor: hasInputNameError('lastName')  ? 'red !important' : ''}">
                                    <!-- <div v-if="personal_info_part.lastName == '' && isSubmitted2 == true" class="text-small invalid-feedback text-left d-block">This field is required.</div> -->
                                    <div v-if="personal_info_part.lastName == '' && isSubmitted2 == true"
                                         class="text-small invalid-feedback text-left d-block" id="prp-helper-input-lastname-required">This field is required.
                                    </div>
                                    <div v-if="!isAlpha(personal_info_part.lastName)" class="text-small invalid-feedback text-left d-block"
                                         :style="{display: personal_info_part.lastName === '' ? 'none !important' : 'inline !important'}" id="prp-helper-input-lastname-alphabets">
                                        This field only allows alphabet characters
                                    </div>
                                </div>
                            </div>
                            <div class="group">
                                <label for="gender" class="text-15px pb-1" id="prp-label-input-gender">Gender</label>
                                <div class="gender-box-disabled text-center input-group d-flex flex-row justify-content-between">
                                    <label class="w-50 z-index1">
                                        <div class="form-check-inline w-100">
                                            <input type="radio" id="gender" name="genderOptions" v-model="personal_info_part.gender" value="male">
                                            <div class="box-disabled border-primary fw-mh d-flex flex-column justify-content-center align-items-center">
                                                <span class="text-small" id="prp-value-input-gender-male">Male</span>
                                            </div>
                                        </div>
                                    </label>
                                    <label class="w-50 z-index1">
                                        <div class="form-check-inline w-100">
                                            <input type="radio" id="genderF" name="genderOptions" v-model="personal_info_part.gender" value="female">
                                            <div class="box-disabled border-primary fw-mh d-flex flex-column justify-content-center align-items-center">
                                                <span class="text-small" id="prp-value-input-gender-female">Female</span>
                                            </div>
                                        </div>
                                    </label>
                                </div>
                            </div>
                            <div class="group">
                                <label class="text-15px pb-1" for="dateOfBirth" id="prp-label-input-dob">Date of Birth</label>
                                    <div class="d-flex" style="height:48px;">
                                        <input v-bind:max="todayDate" v-bind:min="minDate" type="text" class="form-control" v-model="personal_info_part.dob"
                                           id="dateOfBirth" placeholder="DD/MM/YYYY" style="font-style:italic;border-top-right-radius: 0px;border-bottom-right-radius: 0px;"
                                           :style="{borderColor: hasInputDobError() ? 'red !important' : ''}" readonly>
                                           
                                        <div class="d-flex align-items-center justify-content-center calendar-icon" style="width:48px;border-top-right-radius: 0.25rem;border-bottom-right-radius: 0.25rem;" @click="$('#dateOfBirth').click();"><img src="assets/images/calender-icon.svg" class="img-fluid"
                                                id="toggle-btn-calender" alt="Calender icon"></div>
                                        
                                    </div>
                                    <div v-if="personal_info_part.dob == '' && isSubmitted2 == true"
                                         class="text-small invalid-feedback text-left d-block" id="prp-helper-input-dob-required">This field is required.
                                    </div>
                                    <div v-if="isExceeded(personal_info_part.dob) == false" class="text-small invalid-feedback text-left d-block"
                                         :style="{display: personal_info_part.dob === '' ? 'none !important' : 'inline !important'}" id="prp-helper-input-dob-future">
                                        Date of birth cannot be a future date.
                                    </div>

                                    <div v-if="isLessThanMinDate(personal_info_part.dob)" class="text-small invalid-feedback text-left d-block"
                                        :style="{display: personal_info_part.dob === '' ? 'none !important' : 'inline !important'}" id="prp-helper-input-dob-invalid">
                                        Please enter a valid date of birth.
                                    </div>
                            </div>
                            <div class="group group-country">
                                <label for="country"
                                       class="text-15px pb-1" id="prp-label-input-country">Country of Residence
                                </label>
                                <div class="input-group form-group">
                                    <input autocomplete="false" autocomplete="off" readonly type="text" class="input-transparent-line form-control" id="country"
                                           v-model="personal_info_part.country.name" style="height: 2.4rem;"
                                           :style="{borderColor: hasInputStateError('country') ? 'red !important' : ''}">
                                </div>
                                <div v-if="personal_info_part.country == '' && isSubmitted2 == true"
                                     class="text-small invalid-feedback text-left d-block" id="prp-helper-input-country-required">This field is required.
                                </div>
                                <div v-if="isNumeric(personal_info_part.country.name)" class="text-small invalid-feedback text-left d-block"
                                     :style="{display: personal_info_part.country.name === '' ? 'none !important' : 'inline !important'}" id="prp-helper-input-country-alphabet">
                                    This field only allows alphabet characters
                                </div>
                            </div>
                            <div class="group group-nationality">
                                <label for="nationality"
                                       class="text-15px pb-1" id="prp-label-input-nationality">Nationality
                                </label>
                                <div class="input-group form-group">
                                    <input autocomplete="false" autocomplete="off" readonly  type="text" class="input-transparent-line form-control"
                                           v-model="personal_info_part.nationality.name" id="nationality"
                                           style="height: 2.4rem;" :style="{borderColor: hasInputStateError('nationality') ? 'red !important' : ''}">
                                </div>
                                <div v-if="personal_info_part.nationality == '' && isSubmitted2 == true"
                                     class="text-small invalid-feedback text-left d-block" id="prp-helper-input-nationality-required">This field is required.
                                </div>
                                <div v-if="isNumeric(personal_info_part.nationality.name)" class="text-small invalid-feedback text-left d-block"
                                     :style="{display: personal_info_part.nationality.name === '' ? 'none !important' : 'inline !important'}" id="prp-helper-input-nationality-alphabet">
                                    This field only allows alphabet characters
                                </div>
                            </div>
                            <div class="group form-group">
                                <label for="idNumber"
                                       class="text-15px pb-1" id="prp-label-input-idnumber">ID Number
                                </label>
                                <br>
                                <span class="text-15px text-secondary-dark" id="prp-helper-input-idnumber-wrong">Wrong ID may result in missing results.</span>
                                <br>
                                <div class="pt-1 pb-2">
                                    <div class="row">
                                        <div class="col-5 col-sm-5 width-sm">
                                            <select name="idNumberType" id="idNumberType" v-model="personal_info_part.idNumberType" class="form-select form-select-lg" aria-label="ID Number Select">
                                                <option value="NRIC/FIN">NRIC/FIN</option>
                                                <option value="Passport">Passport</option>
                                            </select>
                                        </div>
                                        <div class="col-7 col-sm-7 pl-0 width-sm">
                                            <input @input="upper" v-model="personal_info_part.idNumber" type="text"
                                                   class="form-control" placeholder="E.g.S1234567D" name="idNumber"
                                                   id="idNumber" :style="{borderColor: !validateNumber() && isSubmitted2 == true ? 'red !important' : ''}">
                                        </div>
                                    </div>
                                </div>
                                <div v-if="!validateNumber() && isSubmitted2 == true"
                                     class="text-small invalid-feedback text-left d-block" id="prp-helper-input-incorrect">Please input correct number.
                                </div>

                            </div>
                            <div class="group" id="group-phone">
                                <label for="country" class="text-15px pb-1" id="prp-label-input-phone">Phone
                                </label>
                                <div class="input-group">
                                    <input @paste="onPastingPhoneNumber" @blur="trimMobileCountryCode(personal_info_part.phone.code, personal_info_part.phone.number)"  @change="trimMobileCountryCode(personal_info_part.phone.code, personal_info_part.phone.number)" @keypress="onlyNumber" type="tel" class="input-transparent-line form-control" id="phone"
                                           v-model="personal_info_part.phone.number" style="height: 2.4rem;"
                                           :style="{borderColor: personal_info_part.phone.number == '' && isSubmitted2 == true ? 'red !important' : ''}">
                                </div>
                                <div v-if="hasPhoneError() && isSubmitted2 === true"
                                     class="text-small invalid-feedback text-left d-block" id="prp-helper-input-phone-required">{{errorMessagePhone}}
                                </div>
                            </div>
                            <div class="group d-grid">
                                <div id="recaptcha-element"></div>
                                <input v-model="token" type="hidden"/>
                                <input aria-hidden="true" type="hidden" id="token" ref="refToken"/>
                            </div>

                            <div class="group d-grid mt-5">
                                <button type="submit" @click.prevent="onSubmit()"
                                v-bind:disabled="hasInputAllBlank()"
                                        class="btn btn-primary text-white mx-auto col-10" id="prp-btn-submit-2">Submit
                                </button>
                            </div>
                        </form>
                    </div>
                    <div id="verification-part" class="content" role="tabpanel"
                         aria-labelledby="verification-part-trigger" v-if="dataStep == 3">
                        <div class="d-flex justify-content-center mb-2 mt-2 p-2">
                            <span class="text-28px text-grey-dark" id="prp-label-title-step3">Enter your OTP</span>
                        </div>
                        <div v-if="showErrorMessage" class="alert alert-danger alert-dismissible fade alert-error show" role="alert">
                            <img src="assets/images/fail.svg" id="prp-img-title-step1-1" />
                            <span id="error-message-otp">{{errorMessage}}</span>
                            <!-- <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="prp-btn-title-step-2-2"></button> -->
                        </div> 

                        <div v-if="resendAttempt < 3 && resendAttemptCall === 0"
                             class="text-center text-grey text-15px mb-4 {{otpMessageClass}}" id="otpMessage">{{otpMessage}}</div>
                        <div class="text-center text-black text-15px mb-4 {{otpMessageClass}}" v-if="resendAttempt > 1 && countdown <= 0" id="prp-helper-input-otp-unable">
                            It looks like you are unable to receive an SMS OTP. <br>
                            <div class="mt-2">You can choose to receive your OTP by a voice call by tapping below.</div>
                        </div>

                        <div v-if="enabledOTPCall" class="text-center mb-4">
                            <div v-if="countdown <= 0  && countdownCall > 0  && resendAttempt >= 2" id="countdownID"
                                 class="text-15px text-secondary-dark-grey text-center mt-3">
                                {{textCall}} in : {{ countdownCall }}.
                            </div>
                            <a v-if="countdown <= 0 && countdownCall <= 0 && resendAttempt >= 2" @click.prevent="onResendOTPCall()"
                               id="resendCall" class="link dh-link text-center text-15px">{{textCall}}
                            </a>
                        </div>

                        <form id="form-otp">
                            <div class="mb-3">
                                <!-- <div for="exampleInputEmail1" class="form-label text-dark fw-mh text-center h3" id="prp-label-otp">Enter your OTP</div> -->
                                <div :class="enabledSixboxOTP ? 'digit-group flex justify-center pt-4 text-center' : 'digit-group-single flex justify-center pt-4 text-center'" v-bind:id="OTPInput">
                                        <input @keypress="onlyNumber" @click="clearOTPInput(0)" @input="onFocusOTP(0)" @paste="onPasteOTP"
                                           @keydown="handleBackSpace" :type="enabledSixboxOTP? 'number' : 'tel'" :class="enabledSixboxOTP? 'digit-group fw-600' : 'digit-group-single fw-600'" id="otp0" v-model="OTPData.otp0" ref="otp0"
                                            autocomplete="one-time-code" :maxlength="enabledSixboxOTP? 1 : 6" />

                                        <input @keypress="onlyNumber" @click="clearOTPInput(1)" @input="onFocusOTP(1)" @paste="onPasteOTP"
                                            @keydown="handleBackSpace" type="number" :class="enabledSixboxOTP ? 'd-inline-block fw-600' : 'd-none fw-600'"  id="otp1" v-model="OTPData.otp1" ref="otp1"
                                            inputmode="numeric" autocomplete="one-time-code" maxlength="1" />
                                        <input @keypress="onlyNumber" @click="clearOTPInput(2)" @input="onFocusOTP(2)" @paste="onPasteOTP"
                                            @keydown="handleBackSpace" type="number" :class="enabledSixboxOTP ? 'd-inline-block fw-600' : 'd-none fw-600'" id="otp2" v-model="OTPData.otp2" ref="otp2"
                                            inputmode="numeric" autocomplete="one-time-code" maxlength="1" />
                                        <input @keypress="onlyNumber" @click="clearOTPInput(3)" @input="onFocusOTP(3)" @paste="onPasteOTP"
                                            @keydown="handleBackSpace" type="number" :class="enabledSixboxOTP ? 'd-inline-block fw-600' : 'd-none fw-600'" id="otp3" v-model="OTPData.otp3" ref="otp3"
                                            inputmode="numeric" autocomplete="one-time-code" maxlength="1" />
                                        <input @keypress="onlyNumber" @click="clearOTPInput(4)" @input="onFocusOTP(4)" @paste="onPasteOTP"
                                            @keydown="handleBackSpace" type="number" :class="enabledSixboxOTP ? 'd-inline-block fw-600' : 'd-none fw-600'" id="otp4" v-model="OTPData.otp4" ref="otp4"
                                            inputmode="numeric" autocomplete="one-time-code" maxlength="1" />
                                        <input @keypress="onlyNumber" @click="clearOTPInput(5)" @input="onFocusOTP(5)" @paste="onPasteOTP"
                                            @keydown="handleBackSpace" type="number" :class="enabledSixboxOTP ? 'd-inline-block fw-600' : 'd-none fw-600'" id="otp5" v-model="OTPData.otp5" ref="otp5"
                                            inputmode="numeric" autocomplete="one-time-code" maxlength="1" />
                                </div>
                            </div>

                            <div class="form-text text-center mt-3">
                                <div v-if="countdown > 0  && resendAttempt < 2" id="countdownID"
                                     class="text-13px text-secondary-dark-grey text-center mt-3">
                                    Resend the OTP to my mobile phone in: {{ countdown }}.
                                </div>
                                <div v-if="countdown > 0  && resendAttempt === 2" id="countdownID"
                                     class="text-13px text-secondary-dark-grey text-center mt-3">
                                    Call me with an OTP in: {{ countdown }}.
                                </div>
                                <a v-if="countdown <= 0 && resendAttempt <2" @click.prevent="onResendOTP()" id="resendOTP"
                                   class="link dh-link text-center text-15px">Resend the OTP to my mobile phone
                                </a>
                            </div>

                            <div class="mt-5 mx-3">
                                <div id="verify-help" class="form-text text-center mb-3 d-none" id="prp-helper-input-otp-reminder">If you have not receive
                                    an OTP email in your inbox,
                                    please check your spam or junk folder.
                                </div>
                                <input type="text" ref="focusTemp" style="position:fixed;top:-100px;" />
                                <button :disabled="disabledSubmitButton" ref="verification_button" type="submit" @click.prevent="onVerification()"
                                        class="btn btn-primary w-100 mb-3 border border-1 py-2 text-white" id="submit-otp">Verify
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<%@include file="footer.jsp" %>
<!-- Modals -->
<%@include file="note-register-modals.jsp" %>
<script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/moment.min.js"></script>
<script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/bootstrap.bundle.min.js"></script>
<script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/countrySelect.min.js"></script>
<script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/countrySelect2.min.js"></script>
<script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/bs-stepper/bs-stepper.min.js"></script>
<script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/gijgo.min.js"></script>
<script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/jquery.inputmask.js"></script>
<script src="<fmt:message key="context.path" bundle="${bundle}" />/js/vendor/daterangepicker.min.js"></script>
<script type="module" src="<fmt:message key="context.path" bundle="${bundle}" />/js/registration.js?deploy=<fmt:message key="static.suffix" bundle="${bundle}" />"></script>
</body>
</html>