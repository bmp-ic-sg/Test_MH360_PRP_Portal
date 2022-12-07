import { analytics, FIREBASE_DEBUGS, moengageConfig } from "./main.js"; 
import { logEvent, setUserProperties, setUserId } from "https://www.gstatic.com/firebasejs/9.8.4/firebase-analytics.js"; 

var main = new Vue({
    el: "#myinfo",
    data: {
        redirectUri : "",
        showErrorMessage : false,
        errorMessage : "",
        firstName: "",
        lastName: "",
        gender: "female",
        dob: "",
        nationality: "",
        mobileCountryCode: "",
        idNumber: "",
        idNumberType: "NRIC/FIN",
        email: "",
        mobileNo: "",
        password: "",
        rePassword: "",
        race: "",
        countries: [],
        countdown : 0 || localStorage.getItem("countdown"),
        isEmailExists : false,
        isSubmitted1 : false,
        isSubmitted2 : false,
        isAPIError : false,
        successMessage : false,
        myInfo : false,
        consentMarketing : false,
        agreeReceiveMarketing : false,
        countryIso: "",
        isLoading : true,
        PasswordValidationData : {
            hasNumber : false,
            hasUppercase: false,
            hasLowercase: false,
            hasSpecialChar: false,
            has8Chars: false
        },
        dataStep: 1,
        account_info_part: {
            email: '',
            password: '',
            showPassword: false,
            flexCheckChecked: '',
            flexCheckChecked2: ''
        },
        personal_info_part: {
            firstName: '',
            lastName: '',
            gender: "male",
            country: {
                code : "sg",
                name : "Singapore",               
            },
            nationality: {
                code : "sg",
                name: "Singaporean"
            },
            phone: {
                code : "65",
                number : "",
            },
            dob : '',
            idNumber : '',
            idNumberType: 'NRIC/FIN'
        },
        itl : null,
        showPhoneInput : false,
        errorMessageAlertClass : "alert-danger",
        errorMessageIcon : "fail.svg",
        enabledSixboxOTP: false,
        resendAttempt : 0,
        OTPInput: "OTPInput",
        textCall: "Call me with an OTP",
        defaultCountdown: OTP_VALIDITY,
        defaultCountdownCall: OTP_VALIDITY,
        countDownTemp: null,
        countDownCallTemp: null,
        countdown: sessionStorage.getItem("countdown") === null ? 0 : sessionStorage.getItem("countdown"),
        countdownCall: sessionStorage.getItem("countdownCall") === null ? 0 : sessionStorage.getItem("countdownCall"),
        resendAttempt: sessionStorage.getItem("resendAttempt") === null ? 0 : sessionStorage.getItem("resendAttempt"),
        resendAttemptCall: sessionStorage.getItem("resendAttemptCall") === null ? 0 : sessionStorage.getItem("resendAttemptCall"),
        enabledOTPCall: true,
        otpMessage: "A one-time PIN has been sent via SMS to your mobile number",
        otpMessageClass: " text-success ",
        OTPData: {
            otp0: "",
            otp1: "",
            otp2: "",
            otp3: "",
            otp4: "",
            otp5: "",
        },
        disabledSubmitButton: true,
        errorMessagePhone : "",
        errorMap: [
            "Please provide a valid number.",
            "Invalid country code.",
            "The number is too short.",
            "The number is too long.",
            "Please provide a valid number.",
            "Please provide a valid number.",
        ],
        token : ""
    },
    mounted: function () {
        let vm = this;
        window.vuePRP = this;
        vm.pageview();
        vm.getMyInfoUser();
        vm.getRedirectUrl();
        vm.initCountries();
        vm.isLoading = false;
        if (vm.dataStep === 3) {
            setTimeout(function () {
                vm.$refs.otp0.focus();
            }, 100);

            // set default countdown
            if (sessionStorage.getItem("resendAttempt") !== null) {
                vm.defaultCountdown = sessionStorage.getItem("resendAttempt");
            }

            if (sessionStorage.getItem("countdownCall") !== null) { // set default countdown call
                vm.defaultCountdownCall = sessionStorage.getItem("countdownCall");
                vm.setCountDownCall();
            }

            // set default message
            vm.otpMessage = vm.sentMessage;

            //count down start at the first time
            if (sessionStorage.getItem("resendAttempt") === null)
                this.setCountDown();

            vm.setOTPCallLabel();
        }
    },
  
   
   
    watch: {
        errorMessageAlertClass:function(val){
            if (val === "alert-danger"){
                this.errorMessageIcon = "fail.svg"
            } else if (val === "alert-warning") {
                this.errorMessageIcon = "warning.svg"
            }
        },
        "personal_info_part.nationality":function(val){
        },
        dataStep: function (val) {
            let vm = this;
            if (val == "2"){
                if (vm.personal_info_part.phone.code === ""  || vm.personal_info_part.phone.number ===""){
                    vm.showPhoneInput = true;
                    vm.showErrorMessage = true;
                    vm.errorMessage = "Set up your login credentials and update your required personal details to complete your registration.";
                    vm.errorMessageAlertClass = "alert-warning";
                }   
                vm.loadJS("https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit", true);
                vm.setIntlInput();
            }
            else if (val == "3"){
                // alert('gas!');
                //focust to first input
                // document.getElementById("otp-field0").focus();
            }


        },
        "OTPData.otp0": function (val) {
            if (this.enabledSixboxOTP) {
                this.handleKeyboardPaste(val);
                this.checkDisabledSubmit();
                if (val !== "") {
                    this.$refs.otp1.focus();
                }
            } else {
                // re-validate typing number
                this.OTPData.otp0 = this.OTPData.otp0.replace(/\D/g, ''); // remove all character except number
                this.checkDisabledSubmit();
            }
        },
        "OTPData.otp1": function (val) {
            this.handleKeyboardPaste(val)
            this.checkDisabledSubmit();
            if (val !== "") {
                this.$refs.otp2.focus();
            }
        },
        "OTPData.otp2": function (val) {
            this.handleKeyboardPaste(val);
            this.checkDisabledSubmit();
            if (val !== "") {
                this.$refs.otp3.focus();
            }
        },
        "OTPData.otp3": function (val) {
            this.handleKeyboardPaste(val);
            this.checkDisabledSubmit();
            if (val !== "") {
                this.$refs.otp4.focus();
            }
        },
        "OTPData.otp4": function (val) {
            this.handleKeyboardPaste(val);
            this.checkDisabledSubmit();
            if (val !== "") {
                this.$refs.otp5.focus();
            }
        },
        "OTPData.otp5": function (val) {
            this.handleKeyboardPaste(val);
            this.checkDisabledSubmit();
            let vm = this;
            if (val !== "") {
                let OTPData = (vm.OTPData.otp0 + "" + vm.OTPData.otp1 + "" + vm.OTPData.otp2 + "" + vm.OTPData.otp3 + "" + vm.OTPData.otp4 + "" + vm.OTPData.otp5).toString();
                if (OTPData.length === 6) {
                    vm.$refs.focusTemp.focus();
                    vm.disabledSubmitButton = false;
                    vm.$refs.verification_button.focus();
                } else {
                    let emptyData = "";
                    for (let index = 0; index < 6; index++) {
                        if (vm.OTPData["otp" + index] == "") {
                            emptyData = "otp" + index;
                            break;
                        }
                    }
                    if (emptyData != "") {
                        vm.checkDisabledSubmit();
                        vm.$refs[emptyData].focus();
                    } else {
                        vm.$refs.verification_button.focus();
                    }
                }

            }
        },
        "resendAttempt": function (val) {
            let vm = this;
            sessionStorage.setItem("resendAttempt", val);
            if (vm.enabledOTPCall) {
                if (val === 1) {
                    vm.otpMessage = vm.sentMessage;
                    vm.defaultCountdown = OTP_VALIDITY; // set 1st attemp
                } else if (val === 2) {
                    vm.defaultCountdown = OTP_VALIDITY // set 2nd attemp
                } else {
                    vm.otpMessage = "";
                }
            }
            vm.countdown = vm.defaultCountdown;
            vm.setCountDown();
        },
        resendAttemptCall: function (val) {
            let vm = this;
            sessionStorage.setItem("resendAttemptCall", val);
            vm.setOTPCallLabel();
        },
        "account_info_part.password" : function(val){
            this.passwordMeter();
        },
        // "account_info_part.showPassword" : function(val){
        //     if (val){
        //         document.getElementById("password").setAttribute("type", "password");
        //     }else{
        //         document.getElementById("password").setAttribute("type", "text");
        //     }
        // }
    },
    methods: {
        triggerToken() {
            let vm = this;
            vm.token = "triggerToken";
        },
        hasInputAllBlank() {
            let vm = this;
            if (vm.$refs.refToken) {
                if (vm.$refs.refToken.value == '') return true;
            }
            if (vm.personal_info_part.phone.number === ''){
                return true;
            } 
            if (vm.account_info_part.email === '' ){
                return true;
            }
            if (!vm.PasswordValidationData.hasNumber){
                return true;
            } 
            if (!vm.PasswordValidationData.hasUppercase){
                return true;
            }  
            if (!vm.PasswordValidationData.hasSpecialChar){
                return true;
            }
            if (!vm.PasswordValidationData.has8Chars){
                return true;
            }
            return false;
        },
        loadJS(FILE_URL, async = true) {
            let scriptEle = document.createElement("script");
            scriptEle.setAttribute("src", FILE_URL);
            scriptEle.setAttribute("type", "text/javascript");
            scriptEle.setAttribute("async", async);
            document.body.appendChild(scriptEle);
            // success event 
            scriptEle.addEventListener("load", () => { });
            // error event
            scriptEle.addEventListener("error", (ev) => { console.log("Error on loading file", ev); });
        },
        scrollToElement(id) {
            const container = this.$el.querySelector(id);
            container.scrollIntoView({ behavior: "smooth" });
            container.focus()
        },
        hasPhoneError: function (name) {
            let vm = this;
            if (vm.itl) {
                var errorCode = vm.itl.getValidationError();
                if (vm.personal_info_part.phone.number == '') {
                    vm.errorMessagePhone = "This field is required.";
                    return true;
                } else if (!vm.itl.isValidNumber()) {
                    vm.errorMessagePhone = vm.errorMap[errorCode];
                    return true;
                }
            }

            return false;
        },
        closeAlert(){
            this.showErrorMessage = false;
        },
        initCountries() {
            let vm = this;
            axios.get(CONTEXT_URL + "/rest/sdms/countries").then(function (responseCountries) {
                axios.get(CONTEXT_URL + "/rest/sdms/nationalities").then(function (responseNationalities) {
                    vm.countries = responseCountries;
                    vm.nationalities = responseNationalities;
                    vm.setIntlInput();
                    vm.isLoading = false;
                });
            });
        },
        checkregexNRIC(IdentificationNumber) {
            if (IdentificationNumber.length !== 9) return false;
            var icArray = new Array(9);
            for (let i = 0; i < 9; i++) {
                icArray[i] = IdentificationNumber.charAt(i);
            }
            icArray[1] *= 2;
            icArray[2] *= 7;
            icArray[3] *= 6;
            icArray[4] *= 5;
            icArray[5] *= 4;
            icArray[6] *= 3;
            icArray[7] *= 2;
            var weight = 0;
            for (let i = 1; i < 8; i++) {
                weight += parseInt(icArray[i], 10);
            }
            var offset = (icArray[0] == "T" || icArray[0] == "G") ? 4 : (icArray[0] == "M") ? 3 : 0;
            var temp = (offset + weight) % 11;
            if (icArray[0] == "M") temp = 10 - temp;
            var st = Array("J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A");
            var fg = Array("X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K");
            var m = Array("K", "L", "J", "N", "P", "Q", "R", "T", "U", "W", "X");
            var theAlpha;
            if (icArray[0] == "S" || icArray[0] == "T") theAlpha = st[temp];
            else if (icArray[0] == "F" || icArray[0] == "G") theAlpha = fg[temp];
            else if (icArray[0] == "M") theAlpha = m[temp];
            return (icArray[8] == theAlpha);
        },
        setOTPCallLabel() {
            let vm = this;
            if (vm.resendAttemptCall > 0) {
                vm.textCall = "Call me again";
            } else {
                vm.textCall = "Call me with an OTP";
            }
        },
        setCountDownCall: function () {
            let vm = this;
            vm.countdownCall = vm.defaultCountdownCall;
            vm.countDownCallTemp = setInterval(function () {
                vm.countdownCall = vm.countdownCall - 1;
                sessionStorage.setItem("countdownCall", vm.countdownCall);
                if (vm.countdownCall <= 0) {
                    clearInterval(vm.countDownCallTemp);
                    sessionStorage.removeItem("countdownCall");
                }
            }, 1000);
        },
        onResendOTPCall() {
            let vm = this;
            vm.isLoading = true;
            if (vm.textCall === "Call me with an OTP") {
                vm.callWithOTP();
            } else {
                vm.reCallWithOTP();
            }
        },
        reCallWithOTP() {
            let vm = this;
            axios.post(CONTEXT_URL + "/rest/otp/resend/voice", {
                mobileNo: vm.personal_info_part.phone.number.replace(/\s/g, ""),
                mobileCountryCode: vm.personal_info_part.phone.code,
            }).then(function (response) {
                vm.isLoading = false;
                if (response.status === 200) {
                    vm.showErrorMessage = false;
                    vm.cleanOTP();
                    vm.setCountDownCall();
                    vm.setOTPCallLabel();
                    setTimeout(function () {
                        vm.$refs.otp0.focus();
                    }, 100);
                    // #25 Event Note : click "call me with an OTP" or "Call me again"
                    let payload = {
                        source: '360 Mobile App',
                        page_location: '/otp',
                        link_id: 'otp_call_me',
                        content_type: 'link',
                        screen_name: 'Enter your OTP',
                        content_group: 'registration_login',
                        user_id: vm.account_info_part.email
                    };
                    
                    logEvent(analytics, 'click', payload);// firebase event
                    moe(moengageConfig).track_event("click", payload); // moengage event
                }
            }).catch((error) => {
                vm.isLoading = false;
                vm.showErrorMessage = true;
                vm.errorMessageAlertClass = "alert-danger";
                vm.errorMessage = "Error Occured";
                if (error.response) {
                    if (error.response.data.description != "")
                        vm.errorMessage = error.response.data.description;
                    else
                        vm.errorMessage = "Error Occured";
                } else {
                    vm.errorMessage = "Error Occured";
                }
            });
        },
        callWithOTP() {
            let vm = this;
            axios.post(CONTEXT_URL + "/rest/otp/request/voice").then(function (response) {
                vm.isLoading = false;
                if (response.status === 200) {
                    vm.showErrorMessage = false;
                    vm.resendAttemptCall++;
                    vm.cleanOTP();
                    vm.setCountDownCall();
                    vm.setOTPCallLabel();
                    setTimeout(function () {
                        vm.$refs.otp0.focus();
                    }, 100);

                    // #25 Event Note : click "call me with an OTP" or "Call me again"
                    let payload = {
                        source: '360 Mobile App',
                        page_location: '/otp',
                        link_id: 'otp_call_me',
                        content_type: 'link',
                        screen_name: 'Enter your OTP',
                        content_group: 'registration_login',
                        user_id: vm.account_info_part.email
                    };
                    
                    logEvent(analytics, 'click', payload);// firebase event
                    moe(moengageConfig).track_event("click", payload); // moengage event
                }
            }).catch((error) => {
                vm.isLoading = false;
                vm.showErrorMessage = true;
                vm.errorMessage = "Error Occured";
                if (error.response) {
                    if (error.response.data.description != "")
                        vm.errorMessage = error.response.data.description;
                    else
                        vm.errorMessage = "Error Occured";
                } else {
                    vm.errorMessage = "Error Occured";
                }
            });
        },
        checkDisabledSubmit() {
            let vm = this;
            if (vm.enabledSixboxOTP) {
                if (vm.OTPData.otp0 === "" || vm.OTPData.otp1 === "" || vm.OTPData.otp2 === "" || vm.OTPData.otp3 === "" || vm.OTPData.otp4 === "" || vm.OTPData.otp5 === "") {
                    vm.disabledSubmitButton = true;
                }
                else {
                    vm.disabledSubmitButton = false;
                }
            } else {
                if (vm.OTPData.otp0 === "") {
                    vm.disabledSubmitButton = true;
                }
                else {
                    vm.disabledSubmitButton = false;
                }
            }

        },
        onPasteOTP(e) {
            let vm = this;
            vm.cleanOTP();
            let pastedValue = ((event.clipboardData || window.clipboardData).getData('text')).trim();
            pastedValue = pastedValue.replace(/\D/g, ''); // remove all character except number
            if (vm.enabledSixboxOTP === false) {
                if (pastedValue.length === 6)
                    vm.handlePaste(pastedValue);
                else {
                    setTimeout(() => {
                        $("#otp0").val(0);
                        vm.OTPData.otp0 = "";
                    }, 100);
                }
            } else {
                vm.handlePaste(pastedValue);
            }
        },
        handleBackSpace(e) {
            let vm = this;
            if (e.key === "Backspace" || e.key === "Delete") {
                let currentID = parseInt(e.target.id.substr(-1));
                vm.OTPData["otp" + currentID] = "";
                let previousID = currentID - 1
                let ref = "otp" + previousID;
                if (currentID > 0) {
                    vm.$refs[ref].focus();
                    vm.OTPData["otp" + previousID] = "";
                }
                return e.preventDefault(); // Don't do anything to the input value
            } else {
                vm.checkDisabledSubmit();
            }
        },
        clearOTPInput(otpIndex) {
            let vm = this;
            let emptyData = 0;
            for (let index = 0; index <= 5; index++) {
                if (vm.OTPData["otp" + index] == "") {
                    emptyData = "otp" + index;
                    break;
                }
            }
            if (emptyData !== 0) {
                vm.$refs[emptyData].focus();
            } else {
                vm.OTPData["otp" + otpIndex] = "";
                vm.$refs["otp" + otpIndex].focus();
            }
        },
        onFocusOTP(otpIndex) {
            let vm = this;
            vm.OTPData["otp" + (otpIndex + 1)] = "";
        },
        setIntlInput: function () {
            let vm = this;
            if (vm.showPhoneInput){
                try {
                    setTimeout(() => {
                        const input = document.querySelector("#phone");
                        if (vm.itl !== null) {
                            vm.itl.destroy();
                        }
                        let countriesData = [];
                        let countryNameLocalized = [];
                        if (vm.countries.data) {
                            vm.countries.data.forEach(function (v, i) {
                                if (String(v.isoCode).length > 0) {
                                    if (v.isoCode != undefined && v.cor != undefined) countriesData.push(String(v.isoCode).toLowerCase());
                                    if (v.isoCode != undefined && v.cor != undefined) countryNameLocalized[String(v.isoCode).toLowerCase()] = v.cor;
                                }
                            });
                        }
                        vm.itl = window.intlTelInput(input, {
                            separateDialCode: true,
                            preferredCountries: ["sg", "id", "my"],
                            utilsScript: "js/vendor/intl-tel-input/utils.js", // just for formatting/placeholders etc
                            onlyCountries: countriesData,
                            localizedCountries: countryNameLocalized
                        });

                        if (vm.personal_info_part.phone.code !== null) {
                            let phoneNumber = "";
                            if (String(vm.personal_info_part.phone.code).length > 0 && String(vm.personal_info_part.phone.number).length > 0) 
                                phoneNumber = "+" + vm.personal_info_part.phone.code + " " + vm.personal_info_part.phone.number;
                            vm.itl.setNumber(phoneNumber);
                        }

                        input.addEventListener("countrychange", function () {
                            setTimeout(function () {
                                vm.personal_info_part.phone.code = vm.itl.getSelectedCountryData().dialCode;
                                vm.trimMobileCountryCode(vm.personal_info_part.phone.code, vm.personal_info_part.phone.number);
                            }, 200);
                        });


                    }, 100);
                } catch (err) {
                    console.log(err);
                }
            }
        },
        onlyNumber($event) {
            let keyCode = ($event.keyCode ? $event.keyCode : $event.which);
            if ((keyCode < 48 || keyCode > 57)) { // 46 is dot
                $event.preventDefault();
            }
        },
        trimMobileCountryCode: function (code, number) {
            let vm = this;
            number = number.replace(/\-/g, "").replace(/\+/g, "").replace(/\s/g, "").replace(/\0/g, "");
            number = number.substr(0, 1) === "0" ? number.substr(1, 100) : number;
            let lengthCountryCode = code.length;
            if (code === number.substr(0, lengthCountryCode)) { // the number must contain country code
                vm.personal_info_part.phone.number = number.substr(lengthCountryCode, 100);
            } else {
                vm.trimMobileCountryCode(code, vm.personal_info_part.phone.code + "" + number);
            }
        },
        getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
        },
        pageview: function () {
            // #31 Event Note : Review my profile view
            let payload = { 
                source: '360 Mobile App',
                page_location: '/signup-review-my-profile',
                screen_name: 'Review my profile',
                content_group: 'registration_login'
            };
                    
            logEvent(analytics, 'page_view', payload); // firebase event
            moe(moengageConfig).track_event("page_view", payload); // moengage event
        },
        getRedirectUrl: function () {
            var vm = this;
            axios.get(CONTEXT_URL + "/rest/redirect").then((response) => {
                vm.redirectUri = response.data.redirectUri;
                vm.isLoading = false;
            });
        },
        getMyInfoUser: async function () {
            var vm = this;
            try {
                await axios
                    .get(CONTEXT_URL + "/rest/myinfo/user")
                    .then((response) => {
                        if (response.status === 200) {
                            var data = response.data.user;
                            vm.myInfo = response.data.isMyInfo;
                            if (vm.myInfo) {
                                vm.personal_info_part.firstName = data.name;
                                vm.personal_info_part.gender = data.gender;
                                vm.personal_info_part.dob = data.dob;
                                vm.personal_info_part.nationality.name = String(data.nationality).toLowerCase().trim() === "singapore citizen" ? "singaporean" : data.nationality;
                                vm.personal_info_part.country.name = data.country;
                                vm.personal_info_part.idNumber = data.id;
                                vm.personal_info_part.idNumberType = vm.checkregexNRIC(data.id) ? "nric" : "passport";
                                vm.account_info_part.email = (data.email || "").toLowerCase();
                                if (data.mobileCountryCode !== "" && data.mobileNo !== "" ){
                                    vm.personal_info_part.phone.code = data.mobileCountryCode;
                                    vm.personal_info_part.phone.number = data.mobileNo;
                                }
                            }else{
                                window.location.replace(CONTEXT_URL +"/");
                            }
                        }
                    })
                    .catch((error) => {
                        console.log(error);
                    });
                this.setCountries();
            } catch (error) {
                console.log(error);
            }
        },
        togglePassword(){
            let vm = this;
            if (vm.account_info_part.showPassword)  
                vm.account_info_part.showPassword = false;
            else
                vm.account_info_part.showPassword = true;
        },
        setCountDown: function () {
            let vm = this;
            vm.countdown = vm.defaultCountdown;
            vm.countDownTemp = setInterval(function () {
                vm.countdown = vm.countdown - 1;
                sessionStorage.setItem("countdown", vm.countdown);
                if (vm.countdown <= 0) {
                    if (vm.resendAttempt >= 2) {
                        vm.otpMessage = "";
                    }
                    clearInterval(vm.countDownTemp);
                    sessionStorage.removeItem("countdown");
                }
            }, 1000);
        },
        onResendOTP() {
            let vm = this;
            vm.isLoading = true;
            axios.post(CONTEXT_URL + "/rest/otp/resend").then(function (response) {
                vm.isLoading = false;
                if (response.status === 200) {
                    vm.showErrorMessage = false;
                    vm.cleanOTP();
                    $("#otp-field0").focus();
                    vm.enabledOTPCall ? vm.resendAttempt++ : vm.setCountDown();
                    // #24 Event Note : click "resend OTP to mobile" link
                    let payload = {
                        source: '360 Mobile App',
                        page_location: '/otp',
                        link_id: 'otp_resendotp',
                        content_type: 'link',
                        screen_name: 'Enter your OTP',
                        content_group: 'registration_login',
                        user_id: vm.account_info_part.email
                    };
                    
                    logEvent(analytics, 'click', payload); // firebase event
                    moe(moengageConfig).track_event("click", payload); // moengage event

                }
            }).catch((error) => {
                if (error.response) {
                    if (error.response.data.description != "")
                        vm.errorMessage = error.response.data.description;
                    else
                        vm.errorMessage = "Error Occured";
                } else {
                    vm.errorMessage = "Error Occured";
                }
                vm.isLoading = false;
                vm.showErrorMessage = true;
                vm.otpMessage = "";
            });
        },
        requestOTP() {
            let vm = this;
            vm.showErrorMessage = false;
            axios.post(CONTEXT_URL + "/rest/otp/request").then(function (response) {
                vm.isLoading = false;
                if (response.status === 200) {
                    vm.isLoading = false;
                    vm.successMessage = true;
                    vm.dataStep = 3;
                    vm.setCountDown();
                }
            }).catch(function (error) {
                vm.showErrorMessage = true;
                vm.errorMessageAlertClass = "alert-danger";
                vm.errorMessage = "Failed to send message to mobile phone";
                vm.isLoading = false;
                vm.isAPIError = true;
                window.scrollTo(0, 0);
            })
        },
        validateOTP() {
            let vm = this;
            let otpData = "";
            if (vm.enabledSixboxOTP) {
                otpData = vm.OTPData.otp0 + "" + vm.OTPData.otp1 + "" + vm.OTPData.otp2 + "" + vm.OTPData.otp3 + "" + vm.OTPData.otp4 + "" + vm.OTPData.otp5;
            } else {
                otpData = vm.OTPData.otp0;
            }
            return axios.post(CONTEXT_URL + "/rest/otp/validate", {
                otp: otpData
            });
        },
      
        setCountries: function () {
        },
        passwordMeter: function () {
            if (/.{8,}/.test(this.account_info_part.password)) { // 8 chart
                this.PasswordValidationData.has8Chars = true;
            } else {
                this.PasswordValidationData.has8Chars = false;
            }
            
            if (/(?=.*?[A-Z])/.test(this.account_info_part.password)) {
                this.PasswordValidationData.hasUppercase = true;
            } else {
                this.PasswordValidationData.hasUppercase = false;
            }

            if (/(?=.*?[a-z])/.test(this.account_info_part.password)) {
                this.PasswordValidationData.hasLowercase = true;
            } else {
                this.PasswordValidationData.hasLowercase = false;
            }

            if (/(?=.*?[0-9])/.test(this.account_info_part.password)) {
                this.PasswordValidationData.hasNumber = true;
            } else {
                this.PasswordValidationData.hasNumber = false;
            }
            if (/[-!$@%^&*#()_+|~=`{}\[\\\]:";'<>?,.\/]/.test(this.account_info_part.password)) {
                this.PasswordValidationData.hasSpecialChar = true;
            } else {
                this.PasswordValidationData.hasSpecialChar = false;
            }
        },
        onVerification: async function () {
            let vm = this;
            // #27 Event Note : click "Verify"
            let payload = {
                source: '360 Mobile App',
                page_location: '/otp',
                link_id: 'otp_verify_b',
                content_type: 'button',
                screen_name: 'Enter your OTP',
                content_group: 'registration_login',
                user_id: vm.account_info_part.email
            };
            
            logEvent(analytics, 'click', payload); // firebase event
            moe(moengageConfig).track_event("click", payload); // moengage event
            try {
                let redirectURI = vm.redirectUri;
                vm.isLoading = true;
                await vm.validateOTP()
                    .then(function (response) {
                        if (response.status === 200) {
                            vm.commitUser().then(function (responseCommit) {
                                if (responseCommit.status === 200) {
                                    try {
                                        // R2 :  App_Registration_Successful - register_email_successful [start]
                                        moe(moengageConfig).add_unique_user_id(vm.account_info_part.email); // set unique user id
                                        // R2 :  App_Registration_Successful - register_email_successful [end]
                                        // clear sessionStorage
                                        sessionStorage.removeItem("resendAttempt");
                                        sessionStorage.removeItem("resendAttemptCall");
                                        sessionStorage.removeItem("countdown");
                                        sessionStorage.removeItem("countdownCall");
                                        sessionStorage.removeItem("personal_info_part");
                                        sessionStorage.removeItem("account_info_part");
                                        vm.cleanOTP();

                                        // #26  Event Note : OTP is verified
                                        let payloadOtpVerified = {
                                            source: '360 Mobile App',
                                            page_location: '/otp',
                                            link_id: 'otp_success',
                                            content_type: 'api',
                                            screen_name: 'Enter your OTP',
                                            content_group: 'registration_login',
                                            user_id: vm.account_info_part.email
                                        };
                                        logEvent(analytics, 'otp', payloadOtpVerified); // firebase event
                                        moe(moengageConfig).track_event("otp", payloadOtpVerified); // moengage event

                                        // R2 :  App_Registration_Successful - register_email_successful [start]
                                        const payload_register_email_successful = {
                                            Source: "360 Mobile App",
                                            Login_Status: false,
                                            Screen_Name: 'register_email_successful',
                                            First_Session: true,
                                            Method_of_Registration: "Email",
                                            Method_of_LogIn: null,
                                            Email_CheckBox: vm.consentMarketing,
                                            SMS_CheckBox: vm.agreeReceiveMarketing,
                                            Country_of_Residence: vm.personal_info_part.country.name,
                                            Gender: (vm.personal_info_part.gender === "male" ? "Male" : "Female"),
                                            Date_of_Birth: moment(vm.personal_info_part.dob, "DD/MM/YYYY").format("YYYY-MM-DD") + " 08:00:00 AM",
                                            Nationality: vm.personal_info_part.nationality.name.toLowerCase() === "singaporean" ? "SINGAPORE CITIZEN" : vm.personal_info_part.nationality.name,
                                            Event_status: true // set true due to register_email_successful
                                        };
                                        moe(moengageConfig).track_event("App_Registration_Successful", payload_register_email_successful);
                                        // R2 :  App_Registration_Successful - register_email_successful [end]

                                        let myModal = new bootstrap.Modal(document.getElementById('modal-success'), {
                                            keyboard: false,
                                            backdrop: "static"
                                        });
                                        myModal.show();
                                        vm.showErrorMessage = false;
                                        vm.setSessionClear();
                                        vm.isLoading = false;
                                        // redirecting [start]
                                        if (redirectURI !== "") {
                                            // #28 Event Note : Sign up with Email - success. Shows a success message
                                            let payloadFirebase = {
                                                source: '360 Mobile App',
                                                page_location: '/signup-manual-success',
                                                link_id: 'signup_email_success',
                                                content_type: 'api',
                                                screen_name: 'Enter your OTP',
                                                content_group: 'registration_login',
                                                user_id: vm.account_info_part.email
                                            };
                                            let payloadMoengage = {
                                                source: '360 Mobile App',
                                                page_location: '/signup-manual-success',
                                                link_id: 'signup_email_success',
                                                content_type: 'api',
                                                screen_name: 'Enter your OTP',
                                                content_group: 'registration_login',
                                                user_id: vm.account_info_part.email,
                                                consent_email_app: vm.consentMarketing,
                                                consent_sms: vm.agreeReceiveMarketing,
                                                country_residence: true,
                                                mobile_user_id: true,
                                                first_name: true,
                                                last_name: true,
                                                gender: true,
                                                DOB: true,
                                                mobile_no: true
                                            };
                                            if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payloadFirebase));
                                            logEvent(analytics, 'sign_up', payloadFirebase); // firebase event
                                            moe(moengageConfig).track_event("sign_up", payloadMoengage); // moengage event
                                            // Event Note : setUserId
                                            setUserId(analytics, vm.account_info_part.email);
                                            // Event Note : Set user property for user_scoped parameters
                                            setUserProperties(analytics, {
                                                user_id_logged: vm.account_info_part.email,
                                                registered_user: "yes"
                                            });
                                            // remove sessioStorage data
                                            sessionStorage.removeItem("account_info_part");
                                            sessionStorage.removeItem("personal_info_part");
                                            // redirecting after wait 3 seconds
                                            setTimeout(() => {
                                                moe(moengageConfig).destroy_session(); // destroy the session after event sent
                                            }, 1000);

                                            setTimeout(function () {
                                                window.location.href = redirectURI;
                                            }, 2000);
                                        } else {
                                            vm.showErrorMessage = true;
                                            vm.errorMessageAlertClass = "alert-danger";
                                            vm.errorMessage = "Error Occured";
                                        }
                                        // redirecting [end]
                                    } catch (error) {
                                        vm.isLoading = false;
                                        vm.showErrorMessage = true;
                                        vm.errorMessageAlertClass = "alert-danger";
                                        vm.errorMessage = "Error Occured";
                                    }
                                }
                            }).catch(function (error) {
                                vm.cleanOTP();
                                $("#otp0").focus();
                                vm.showErrorMessage = true;
                                vm.errorMessageAlertClass = "alert-danger";
                                vm.errorMessage = "Error Occured";
                                vm.isLoading = false;
                                vm.isAPIError = true;
                            }); // end of commit user
                        }
                    })
                    .catch(function (error) {
                        // #26  Event Note : OTP is verified
                        let payload = {
                            source: '360 Mobile App',
                            page_location: '/otp',
                            link_id: 'otp_fail',
                            content_type: 'api',
                            screen_name: 'Enter your OTP',
                            content_group: 'registration_login',
                            user_id: vm.account_info_part.email
                        };
                        
                        logEvent(analytics, 'otp', payload); // firebase event
                        moe(moengageConfig).track_event("otp", payload); // moengage event

                        // R2 :  App_Registration_Unsuccessful - register_unsuccessful [start]
                        moe(moengageConfig).add_unique_user_id(vm.account_info_part.email); // set unique user id
                        const payload_register_unsuccessful = {
                            Source: "360 Mobile App",
                            Login_Status: false,
                            Screen_Name: 'register_unsuccessful',
                            First_Session: true,
                            Method_of_Registration: "Email",
                            Method_of_LogIn: null,
                            Email_CheckBox: vm.consentMarketing,
                            SMS_CheckBox: vm.agreeReceiveMarketing,
                            Country_of_Residence: vm.personal_info_part.country,
                            Gender: (vm.personal_info_part.gender === "male" ? "Male" : "Female"),
                            Date_of_Birth: moment(vm.personal_info_part.dob, "DD/MM/YYYY").format("YYYY-MM-DD") + " 08:00:00 AM",
                            Nationality: vm.personal_info_part.nationality.name.toLowerCase() === "singaporean" ? "SINGAPORE CITIZEN" : vm.personal_info_part.nationality.name,
                            Event_status: true // set true due to register_unsuccessful
                        };
                        moe(moengageConfig).track_event("App_Registration_Unsuccessful", payload_register_unsuccessful);
                        // R2 :  App_Registration_Unsuccessful - register_unsuccessful [end]

                        if (error.response) {
                            vm.errorMessage = error.response.data.description;
                        } else {
                            vm.errorMessage = "Error Occured";
                        }
                        vm.cleanOTP();
                        $("#otp0").focus();
                        vm.showErrorMessage = true;
                        vm.errorMessageAlertClass = "alert-danger";
                        vm.isLoading = false;
                        vm.isAPIError = true;
                    });
            } catch (error) {
                vm.showErrorMessage = true;
                vm.errorMessageAlertClass = "alert-danger";
                vm.errorMessage = "Error Occured";
                vm.isLoading = false;
                console.error(error);
            }
        },
        cleanOTP() {
            let vm = this;
            vm.OTPData.otp0 = "";
            vm.OTPData.otp1 = "";
            vm.OTPData.otp2 = "";
            vm.OTPData.otp3 = "";
            vm.OTPData.otp4 = "";
            vm.OTPData.otp5 = "";
        },
        getFormatDate() {
            let date = new Date(this.personal_info_part.dob);
            return [
                date.getFullYear(),
                this.padTo2Digits(date.getMonth() + 1),
                this.padTo2Digits(date.getDate()),
            ].join('-');
        },
        padTo2Digits(num) {
            return num.toString().padStart(2, '0');
        },
        commitUserErrorHandling(error){
            let vm = this;
            if (error.response) {
                vm.isEmailExists = false;
                if (error.response.status === 409) { // if duplicated email
                    vm.isEmailExists = true;
                } else { // other error 
                    vm.errorMessage = error.response.data.description
                }
                // #34 Event Note : Sign up success - Singpass(popup) 
                let payload = {
                    source: '360_mobile_app',
                    page_location: '/signup-singpass-success',
                    link_id: 'signup_singpass_fail',
                    content_type: 'api',
                    content_group: 'registration_login',
                    user_id: vm.account_info_part.email
                };
                logEvent(analytics, 'sign_up', payload); // firebase event
                moe(moengageConfig).track_event("sign_up", payload); // moengage event
                
                vm.showErrorMessage = true;
                vm.errorMessageAlertClass = "alert-danger";

                // R2 :  App_Registration_Unsuccessful - register_unsuccessful [start]
                const payload_register_unsuccessful = {
                    Source: "360 Mobile App",
                    Login_Status: "False",
                    Screen_Name: 'register_unsuccessful',
                    First_Session: "True",
                    Method_of_Registration: "Singpass",
                    Method_of_LogIn: null,
                    Email_CheckBox: vm.consentMarketing ? "True" : "False",
                    SMS_CheckBox: vm.agreeReceiveMarketing ? "True" : "False",
                    Country_of_Residence: vm.personal_info_part.country.name,
                    Gender: (vm.personal_info_part.gender === "male" ? "Male" : "Female"),
                    Date_of_Birth: moment(vm.personal_info_part.dob, "YYYY-MM-DD").format("YYYY-MM-DD") + " 08:00:00 AM",
                    Nationality: vm.personal_info_part.nationality.name.toLowerCase() === "singaporean" ? "SINGAPORE CITIZEN" : vm.personal_info_part.nationality.name,
                    Event_status: "True" // set true due to register_unsuccessful
                };
                moe(moengageConfig).track_event("App_Registration_Unsuccessful", payload_register_unsuccessful);
                // R2 :  App_Registration_Unsuccessful - register_unsuccessful [end]

                // Event Note : setUserId
                // setUserId(analytics, vm.account_info_part.email);
                // Event Note : Set user property for user_scoped parameters
                // setUserProperties(analytics,{
                //     user_id_logged: vm.account_info_part.email,
                //     registered_user: "no"
                // });
            }
        },
        commitUser() {
            let vm = this;
            try {
                vm.isLoading = false;
                return axios.post(CONTEXT_URL + "/rest/users/submit");

            } catch (error) {
                console.warn(error);
            }
        },
        setSessionClear: function () {
            let vm = this;
            try {
                return axios.get(CONTEXT_URL + "/rest/sessions/clear");

            } catch (error) {
                vm.showErrorMessage = true;
                vm.errorMessageAlertClass = "alert-danger";
                vm.errorMessage = "Error Occured";
            }
        },
         submitForm(response){
            let vm = this;
            if (response.status === 200) {
                vm.showErrorMessage = false;
                vm.commitUser().then(function (responseCommit) {
                    vm.showErrorMessage = false;
                    if (responseCommit.status === 200) {
                        try {
                            // R2 :  App_Registration_Successful - register_singpass_successful [start]
                            moe(moengageConfig).add_unique_user_id(vm.account_info_part.email); // set unique user id
                            // R2 :  App_Registration_Successful - register_singpass_successful [end]

                            let device_id = vm.getCookie("_ga");
                            // #34 Event Note : Sign up success - Singpass(popup) 
                            let payload = {
                                source: '360 Mobile App',
                                page_location: '/signup-singpass-succes',
                                link_id: 'signup_singpass_success',
                                content_type: 'api',
                                content_group: 'registration_login',
                                user_id: vm.account_info_part.email
                            };
                            logEvent(analytics, 'sign_up', payload); // firebase event
                            moe(moengageConfig).track_event("sign_up", payload); // moengage event

                            // R2 :  App_Registration_Successful - register_singpass_successful [start]
                            let payload_register_singpass_successful = {
                                Source: "360 Mobile App",
                                Login_Status: "False",
                                Screen_Name: 'register_singpass_successful',
                                First_Session: "True",
                                Method_of_Registration: "Singpass",
                                Method_of_LogIn: null,
                                Email_CheckBox: vm.consentMarketing ? "True" : "False",
                                SMS_CheckBox: vm.agreeReceiveMarketing ? "True" : "False",
                                Country_of_Residence: vm.personal_info_part.country.name,
                                Gender: (vm.personal_info_part.gender === "male" ? "Male" : "Female"),
                                Date_of_Birth: moment(vm.personal_info_part.dob, "YYYY-MM-DD").format("YYYY-MM-DD") + " 08:00:00 AM",
                                Nationality: (vm.personal_info_part.nationality.name.toLowerCase() === "singaporean" ? "SINGAPORE CITIZEN" : vm.personal_info_part.nationality.name),
                                Event_status: "True" // set true due to register_singpass_successful
                            }
                            moe(moengageConfig).track_event("App_Registration_Successful", payload_register_singpass_successful);
                            // R2 :  App_Registration_Successful - register_singpass_successful [end]

                            // Event Note : setUserId
                            // setUserId(analytics, vm.account_info_part.email);
                            // Event Note : Set user property for user_scoped parameters
                            setUserProperties(analytics, {
                                // user_id_logged: vm.account_info_part.email,
                                registered_user: "yes",
                                country_residence: vm.personal_info_part.country.name,
                                care_location: "",
                                device_id: device_id,
                                app_version: "",
                                sdk_version: ""
                            });

                            vm.isLoading = false;
                            var myModal = new bootstrap.Modal(document.getElementById('modal-success'), {
                                keyboard: false,
                                backdrop: "static"
                            });
                            myModal.show();
                            vm.setSessionClear();
                            vm.isLoading = false;
                            setTimeout(() => {
                                moe(moengageConfig).destroy_session(); // destroy the session after event sent
                            }, 1000);

                            setTimeout(() => {
                                window.location.replace(vm.redirectUri);
                            }, 2000);

                        } catch (err) {
                            alert(err);
                        }
                    } // endif response get 200
                    // end of commit user
                }).catch((error) => {
                    alert(error);
                    vm.commitUserErrorHandling(error)
                });
            }
        },
        onSubmit: async function () {
            var vm = this;
            vm.isEmailExists = false;
            vm.isSubmitted1 = true;
            if (vm.hasInputEmailError()) {
                const container = this.$el.querySelector("#email");
                container.scrollIntoView({ behavior: "smooth" });
                container.focus()
                return;
            }

            if(vm.hasPhoneError()){
                vm.scrollToElement("#phone");
                return;
            }
            
            vm.isSubmitted2 = true;
            var idNumber = vm.personal_info_part.idNumber.toUpperCase();
            var phoneNumber = vm.personal_info_part.phone.number.replace(/\s/g, "");
            vm.isLoading = true;
            await axios
            .post(CONTEXT_URL + "/rest/users/store", {
                token: vm.$refs.refToken ? vm.$refs.refToken.value : "",
                firstName: vm.personal_info_part.firstName,
                lastName: vm.myInfo ? " " : vm.personal_info_part.lastName,
                email: vm.account_info_part.email,
                dob: vm.getFormatDate(),
                id: idNumber,
                employeeType: vm.personal_info_part.idNumberType,
                mobileNo: phoneNumber,
                password: vm.account_info_part.password,
                mobileCountryCode: vm.personal_info_part.phone.code,
                gender: vm.personal_info_part.gender,
                nationality: vm.personal_info_part.nationality.name,
                country: vm.personal_info_part.country.name,
                consentMarketing: vm.consentMarketing,
                marketingSmsFlag: vm.agreeReceiveMarketing
            })
            .then(function (response) {
                if (response.status === 200) {
                    if (vm.showPhoneInput){
                        vm.requestOTP();
                    } else { // request otp
                        vm.submitForm(response);
                    }
                }
            })
            .catch((error) => {
                vm.storeUserErrorHandling(error)
            })
          
        }, // end of submit
        storeUserErrorHandling: function (error) {
            let vm = this;
            if (error.response) {
                vm.isEmailExists = false;
                vm.showErrorMessage = false;
                if (error.response.status === 403) { //blocked
                    vm.isLoading = true;
                }
                else if (error.response.status === 409) { // if duplicated email
                    vm.isEmailExists = true;
                    vm.isLoading = false;
                    vm.isAPIError = true;
                } else { // other error 
                    vm.errorMessage = error.response.data.description;
                    vm.showErrorMessage = true;
                    vm.errorMessageAlertClass = "alert-danger";
                    vm.isLoading = false;
                    vm.isAPIError = true;
                }

                // #34 Event Note : Sign up success - Singpass(popup) 
                let payload = {
                    source: '360_mobile_app',
                    page_location: '/signup-singpass-success',
                    link_id: 'signup_singpass_fail',
                    content_type: 'api',
                    content_group: 'registration_login',
                    user_id: vm.account_info_part.email
                };
                
                logEvent(analytics, 'sign_up', payload); // firebase event
                moe(moengageConfig).track_event("sign_up", payload); // moengage event
                // Event Note : setUserId
                // setUserId(vm.account_info_part.email);
                // Event Note : Set user property for user_scoped parameters
                // setUserProperties({
                //     user_id_logged: vm.account_info_part.email,
                //     registered_user: "no"
                // });
                // R2 :  App_Registration_Unsuccessful - register_unsuccessful [start]
                const payload_register_unsuccessful = {
                    Source: "360 Mobile App",
                    Login_Status: "False",
                    Screen_Name: 'register_unsuccessful',
                    First_Session: "True",
                    Method_of_Registration: "Singpass",
                    Method_of_LogIn: null,
                    Email_CheckBox: vm.consentMarketing ? "True" : "False",
                    SMS_CheckBox: vm.agreeReceiveMarketing ? "True" : "False",
                    Country_of_Residence: vm.personal_info_part.country.name,
                    Gender: (vm.personal_info_part.gender === "male" ? "Male" : "Female"),
                    Date_of_Birth: moment(vm.personal_info_part.dob, "YYYY-MM-DD").format("YYYY-MM-DD") + " 08:00:00 AM",
                    Nationality: vm.personal_info_part.nationality.name.toLowerCase() === "singaporean" ? "SINGAPORE CITIZEN" : vm.personal_info_part.nationality.name,
                    Event_status: "True" // set true due to register_unsuccessful
                };
                moe(moengageConfig).track_event("App_Registration_Unsuccessful", payload_register_unsuccessful);
                // R2 :  App_Registration_Unsuccessful - register_unsuccessful [end]
            } else {
                vm.errorMessage = "Error Occured";
            }
        },
        hasEmailCorrect : function(){
            return (/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test(this.account_info_part.email);
        },
        hasInputEmailError: function () {
            let vm = this;
            if (vm.account_info_part.email == '' && vm.isSubmitted1 == true ) {
                return true;
            }
            if (!vm.hasEmailCorrect() && vm.isSubmitted1 == true) {
                return true;
            }
            if (vm.isEmailExists) {
                return true;
            }
            return false;

        },
        hasUniqueEmail :  function(){
            let vm = this;
            try {
                vm.isLoading = true;
                return axios .post(CONTEXT_URL + "/rest/users/email/validate", { email: vm.account_info_part.email });
                        
            } catch (error) {
                console.warn(error);
            }
        },
        goToStep: function (step) {
            let vm = this;
            setTimeout(() => {
                if (step == '2'){ // if go to step 2
                    // #32 Event Note : click "continue" 
                    let payload = {
                        source: '360 Mobile App',
                        page_location: '/signup-review-my-profile',
                        link_id: 'signup_reviewprofile_continue_b',
                        content_type: 'button',
                        screen_name: 'Review my profile',
                        content_group: 'registration_login',
                        user_id: vm.account_info_part.email
                    };
                            
                    logEvent(analytics, 'click', payload); // firebase event
                    moe(moengageConfig).track_event("click", payload); // moengage event

                    // R2 :  App_First_Time_Consent_Granted - register_consent_grant_singpass [start]
                    const payload_register_consent_grant_singpass = {
                        Source: "360 Mobile App",
                        Login_Status: "False",
                        Screen_Name: 'register_consent_grant_singpass',
                        First_Session: "True",
                        Method_of_Registration: null,
                        Method_of_LogIn: null,
                        Email_CheckBox: vm.consentMarketing ? "True" : "False",
                        SMS_CheckBox: vm.agreeReceiveMarketing ? "True" : "False",
                        Country_of_Residence: null,
                        Gender: null,
                        Date_of_Birth : null,
                        Nationality: null,
                        Event_status: "True" // set true due to success
                    };
                    moe(moengageConfig).track_event("App_First_Time_Consent_Granted", payload_register_consent_grant_singpass);
                    // R2 :  App_First_Time_Consent_Granted - register_consent_grant_singpass [end]

                    // #33 Event Note : Set my password: View
                    let payloadsetMyPassword = {
                        source: '360 Mobile App',
                        page_location: '/signup-set-my-password',
                        screen_name: 'Set my password',
                        content_group: 'registration_login',
                        user_id: vm.account_info_part.email
                    }
                    if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payloadsetMyPassword));
                    logEvent(analytics, 'page_view', payloadsetMyPassword); // firebase event
                    moe(moengageConfig).track_event("page_view", payloadsetMyPassword); // moengage event
                    vm.dataStep = 2; 
                }  

                if (step == '3'){ // if go to step 3
                    this.isSubmitted2 = true;
                    // Scroll to top after next button is clicked
                    window.scrollTo(0, 0);  
                } 
            }, 200);
        },
        validateNumber: function(){
            let vm = this;
            let idNumber = vm.personal_info_part.idNumber;
            let idNumberType = vm.personal_info_part.idNumberType;
            // idNumber = idNumber.trim().toUpperCase();
            // console.log(vm.personal_info_part);
            return;
            if (idNumberType === "NRIC/FIN") {
                // Initialization
                const checksumArr_ST = ["J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"],
                    checksumArr_FG = ["X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"];

                function checksum(prefix, number) {
                    // Calculate checksum of digital fragment of NRIC/FIN
                    function stringToSum(number) {
                        const multiplyFactors = [2, 7, 6, 5, 4, 3, 2];
                        return number
                            .split("")
                            .map((s) => parseInt(s))
                            .map((digit, i) => digit * multiplyFactors[i])
                            .reduce((a, b) => a + b, 0);
                    }
                    prefix = prefix.toUpperCase();
                    let sum = 0;
                    if (prefix === "T" || prefix === "G") sum = 4; // an offset if start with T/G
                    sum += stringToSum(number);
                    switch (prefix) {
                        case "S":
                        case "T":
                            return checksumArr_ST[sum % 11];
                        case "F":
                        case "G":
                            return checksumArr_FG[sum % 11];
                        default:
                            throw new Error("Invalid Prefix detected");
                    }
                }

                //Check if the format is correct, then check the checksum
                if (
                    !/^[S|T]\d{7}[J|Z|I|H|G|F|E|D|C|B|A]|[F|G]\d{7}[X|W|U|T|R|Q|P|N|M|L|K]$/.test(
                        idNumber
                    )
                ) {
                    return 0;
                }
                const prefix = idNumber[0].toUpperCase();
                const number = idNumber.slice(1, -1);
                const oldChecksum = idNumber.slice(-1).toUpperCase();

                return oldChecksum === checksum(prefix, number)
                    ? true
                    : false;
            } else {
                // Check Passport ID
                return /^(?!^0+$)[a-zA-Z0-9']{4,10}$/.test(idNumber) ? true : false;
            }
        },
    },// end of method
    computed : {
        firstTabInvalid: function () {
            var nationality = $(
                "#nationality"
            ).nationalitySelect(
                "getSelectedNationalityData"
            );
            var country = $("#country").countrySelect(
                "getSelectedCountryData"
            );

        },
    }
});

//     }
// }