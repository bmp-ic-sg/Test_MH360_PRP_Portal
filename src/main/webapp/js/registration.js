import { analytics, FIREBASE_DEBUGS, moengageConfig } from "./main.js"; 
import { logEvent, setUserProperties, setUserId } from "https://www.gstatic.com/firebasejs/9.8.4/firebase-analytics.js"; 

new Vue({
    el: "#register",
    data: {
        disabledSubmitButton : true,
        itl: null,
        otpMessage: "A one-time PIN has been sent via SMS to your mobile number",
        otpMessageClass: " text-success ",
        isEmailExists: false,
        isSubmitted1: false,
        isSubmitted2: false,
        isAPIError: false,
        successMessage: false,
        myInfo: false,
        consentMarketing: false,
        agreeReceiveMarketing: false,
        countryIso: "",
        isLoading: true,
        errorMessage: "",
        showErrorMessage: false,
        PasswordValidationData: {
            hasNumber: false,
            hasUppercase: false,
            hasLowercase: false,
            hasSpecialChar: false,
            has8Chars: false
        },
        dataStep: 0,
        account_info_part: {
            email: "",
            password: '',
            showPassword: false,
            flexCheckChecked: '',
            flexCheckChecked2: ''
        },
        personal_info_part: {
            firstName: '',
            lastName: '',
            gender: 'male',
            country: {
                code: "sg",
                name: "Singapore",
            },
            nationality: {
                code: "sg",
                name: "Singaporean"
            },
            phone: {
                code: "65",
                number: "",
            },
            dob: moment().format('DD/MM/YYYY'),
            idNumber: '',
            idNumberType: 'NRIC/FIN'
        },
        redirectUri: "",
        OTPData: {
            otp0: "",
            otp1: "",
            otp2: "",
            otp3: "",
            otp4: "",
            otp5: "",
        },
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
        hashLocation: "",
        emptyOTP : 0,
        todayDate : "",
        minDate: "1900-01-01",
        enabledSixboxOTP : false,
        countries : [],
        nationalities : [],
        errorMap: [
            "Please provide a valid number.",
            "Invalid country code.",
            "The number is too short.",
            "The number is too long.",
            "Please provide a valid number.",
            "Please provide a valid number.",
        ],
        errorMessagePhone : "",
        token: ""
    },
    mounted: function () {
        let vm = this;
        window.vuePRP = this;
        vm.getRedirectUrl();
        vm.getMyInfoUser();
        vm.isLoading = false;
        // set default step

        vm.setHashLocation();

        vm.getPersonalInfo();
        vm.getAccountInfo();

        if (vm.dataStep === 1) {
            // set data from storage
            setTimeout(function () {
                vm.$refs.email.focus();
            }, 100);
        }

        if (vm.dataStep === 2) {
            vm.setDatepicker();
        }

        // set max date
        vm.todayDate = vm.getFormatDateToday();

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


        window.addEventListener("hashchange", function (params) {
            vm.setHashLocation();
        });

    },

    watch: {
        "personal_info_part.phone.number": function (val) {
            let vm = this;
        },
        defaultCountFail: function (val) {
            let vm = this;
            if (val > 1) {
                $("#otpMessage").hide();
            }
        },
        "personal_info_part.country.code": function (val) {
            let vm = this;
            vm.setIntlInput();
        },
        dataStep: function (val) {
            let vm = this;
            if (val == "1") {
                window.location = '#account-information';
            } else if (val == "2") {
                vm.loadJS("https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit", true);
                vm.initCountries();
                vm.setDatepicker();
                window.location = '#personal-information';

                setTimeout(function () {
                    vm.$refs.firstName.focus();
                    window.scrollTo(0, 0);
                }, 100);

                // send data via firebase


            }
            else if (val == "3") {
                setTimeout(function () {
                    vm.$refs["otp0"].focus();
                }, 100);
                window.location = '#verification';
            }
        },
        "account_info_part.password": function (val) {
            this.passwordMeter();
        },
        "OTPData.otp0": function (val) {
            if (this.enabledSixboxOTP){
                this.handleKeyboardPaste(val);
                this.checkDisabledSubmit();
                if (val !== "") {
                    this.$refs.otp1.focus();
                }
            }else{
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
        "resendAttemptCall": function (val) {
            let vm = this;
            sessionStorage.setItem("resendAttemptCall", val);
            vm.setOTPCallLabel();
        },
        account_info_part: function (val) {
        }
    },
    methods: {
        triggerToken() {
            let vm = this;
            vm.token = "triggerToken";
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
        onPastingPhoneNumber(e){
            let vm = this;
            let pastedValue = ((event.clipboardData || window.clipboardData).getData('text')).trim();
            pastedValue = pastedValue.replace(/\D/g, ''); // remove all character except number
            setTimeout(function(){
                vm.personal_info_part.phone.number = pastedValue;
                vm.trimMobileCountryCode(vm.personal_info_part.phone.code, vm.personal_info_part.phone.number);
            }, 100);
        },
        initCountries(){
            let vm = this;
            axios.get(CONTEXT_URL + "/rest/sdms/countries").then(function (responseCountries) {
                axios.get(CONTEXT_URL + "/rest/sdms/nationalities").then(function (responseNationalities) {
                    vm.countries = responseCountries;
                    vm.nationalities = responseNationalities;
                    vm.setCountries();
                    vm.setIntlInput();
                    vm.isLoading = false;
                });
            });
        },
        setDatepicker: function () {
            let vm = this;
            setTimeout(() => {
                const date = vm.personal_info_part.dob === "" ? moment() : vm.personal_info_part.dob;
                $('#dateOfBirth').daterangepicker({
                        "singleDatePicker": true,
                        "showDropdowns": true,
                        "minYear": 1900,
                        "maxYear": parseInt(moment().format('YYYY'),10),
                        "startDate": date,
                        "endDate": date,
                        "maxDate": moment(),
                        "minDate": "01/01/1900", 
                        "autoApply": true,
                          locale: {
                          format: 'DD/MM/YYYY'
                        }
                         
                    }, function(start, end) {
                      vm.personal_info_part.dob = end.format('DD/MM/YYYY');
                    });
            }, 100 );
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
        upper(e) {
            let vm = this;
            vm.personal_info_part.idNumber = e.target.value.toUpperCase();
        },
        isNumeric: function (value) {
            return /\d/.test(value);
        },
        isAlpha: function (value) {
            return /^[a-zA-Z_ /,'-]*$/.test(value);
        },
        isBlank: function (value) {
            if (typeof value == "string") {
                return value.length === 0;
            } else {
                return true;
            }
        },
        handleKeyboardPaste(pastedValue){
            let vm = this;
            if (pastedValue.length > 5){
                vm.handlePaste(pastedValue); // paste handling whenever one of input contains more that one
            }
        },
        checkDisabledSubmit() {
            let vm = this;
            if (vm.enabledSixboxOTP){
                if (vm.OTPData.otp0 === "" || vm.OTPData.otp1 === "" || vm.OTPData.otp2 === "" || vm.OTPData.otp3 === "" || vm.OTPData.otp4 === "" || vm.OTPData.otp5 === "") {
                    vm.disabledSubmitButton = true;
                }
                else {
                    vm.disabledSubmitButton = false;
                }
            }else{
                if (vm.OTPData.otp0 === "") {
                    vm.disabledSubmitButton = true;
                }
                else {
                    vm.disabledSubmitButton = false;
                }
            }

        },
        onlyNumber($event) {
            let keyCode = ($event.keyCode ? $event.keyCode : $event.which);
            if ((keyCode < 48 || keyCode > 57)) { // 46 is dot
                $event.preventDefault();
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
            if (emptyData !== 0 ){
                vm.$refs[emptyData].focus();
            }else{
                vm.OTPData["otp" + otpIndex] = "";
                vm.$refs["otp" + otpIndex].focus();
            }
        },
        onFocusOTP(otpIndex) {
            let vm = this;
            vm.OTPData["otp" + (otpIndex + 1)] = "";
        },
        getAccountInfo() {
            let vm = this;
            if (sessionStorage.getItem("account_info_part") !== null) {
                let parsedData = JSON.parse(sessionStorage.getItem("account_info_part"));
                if (parsedData !== null) {
                    vm.account_info_part.email = parsedData.email;
                    vm.account_info_part.password = parsedData.password;
                    vm.consentMarketing = parsedData.consentMarketing;
                    vm.agreeReceiveMarketing = parsedData.agreeReceiveMarketing;
                }
            }
        },
        getPersonalInfo() {
            let vm = this;
            let parsedData = JSON.parse(sessionStorage.getItem("personal_info_part"));
            if (parsedData != null) {
                vm.personal_info_part.firstName = parsedData.firstName ? parsedData.firstName : "";
                vm.personal_info_part.lastName = parsedData.lastName;
                vm.personal_info_part.gender = parsedData.gender;
                vm.personal_info_part.country.code = parsedData.country.code;
                vm.personal_info_part.country.name = parsedData.country.name;
                vm.personal_info_part.nationality.code = parsedData.nationality.code;
                vm.personal_info_part.nationality.name = parsedData.nationality.name
                vm.personal_info_part.phone.code = parsedData.phone.code.replace(/\-/g, "").replace(/\+/g, "");;
                vm.personal_info_part.phone.number = parsedData.phone.number.replace(/\-/g, "").replace(/\+/g, "").replace(/\s/g, "").replace(/\0/g, "");
                if (vm.personal_info_part.phone.code != "" && vm.personal_info_part.phone.number != "")
                    vm.trimMobileCountryCode(vm.personal_info_part.phone.code, vm.personal_info_part.phone.number);
                vm.personal_info_part.dob = parsedData.dob;
                vm.personal_info_part.idNumber = parsedData.idNumber;
                vm.personal_info_part.idNumberType = parsedData.idNumberType;
            }
        },
        isExceeded: function () {
            let d1 = moment(this.personal_info_part.dob, "DD/MM/YYYY");
            let today = moment();
            if (d1 > today) {
                return false
            }
        },
        isLessThanMinDate: function () {
            let d1 = moment(this.personal_info_part.dob, "DD/MM/YYYY");
            let minDate = moment(this.minDate, "YYYY-MM-DD");
            return d1 < minDate;
        },
        setHashLocation() {
            let vm = this;
            let hash = location.hash;

            if (hash === "#account-information") {
                vm.dataStep = 1;
                // #17 Event Note : Create my profile view
                let payloadProfileView = {
                    source: '360 Mobile App',
                    page_location: '/signup-create-profile',
                    screen_name: 'create my profile',
                    content_group: 'registration_login',
                    user_id: vm.account_info_part.email
                };
                logEvent(analytics, 'page_view', payloadProfileView); // firebase event
                moe(moengageConfig).track_event("page_view", payloadProfileView); // moengage event
            }
            else if (hash === "#personal-information") {
                vm.dataStep = 2;
                // #18 Event Note : Create my profile: click "continue"
                let payloadStep1Clicked = {
                    source: '360 Mobile App',
                    page_location: '/signup-create-profile',
                    link_id: 'signup_createprofile_next_b',
                    content_type: 'button',
                    screen_name: 'create my profile',
                    content_group: 'registration_login',
                    user_id: vm.account_info_part.email
                };
                if (FIREBASE_DEBUGS) console.log("Firebase Event : "+JSON.stringify(payloadStep1Clicked));
                logEvent(analytics, 'click', payloadStep1Clicked); // firebase event
                moe(moengageConfig).track_event("click", payloadStep1Clicked); // moengage event

                //#19 Event Note : Create my profile pg2
                let payloadStep2PageView = {
                    source: '360 Mobile App',
                    page_location: '/signup-create-profile2',
                    screen_name: 'create my profile',
                    content_group: 'registration_login',
                    user_id: vm.account_info_part.email
                };
                if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payloadStep2PageView));
                logEvent(analytics, 'page_view', payloadStep2PageView);// firebase event
                moe(moengageConfig).track_event("page_view", payloadStep2PageView); // moengage event
            }
            else if (hash === "#verification") {
                if (sessionStorage.getItem("account_info_part") === null) {
                    hash = "#account-information";
                    vm.dataStep = "1";
                }
                else if (sessionStorage.getItem("personal_info_part") === null) {
                    hash = "#personal-information";
                    vm.dataStep = "2";
                }
                else {
                    vm.dataStep = 3;
                    // #22 Event Note : OTP View
                    let payloadStep3PageView = {
                        source: '360 Mobile App',
                        page_location: '/otp',
                        screen_name: 'Enter your OTP',
                        content_group: 'registration_login',
                        user_id: vm.account_info_part.email
                    };
                    if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payloadStep3PageView));
                    logEvent(analytics, 'page_view', payloadStep3PageView);// firebase event
                    moe(moengageConfig).track_event("page_view", payloadStep3PageView); // moengage event
                }
            }
            else
                vm.dataStep = 1;

            vm.hashLocation = hash;
        },
        setOTPCallLabel() {
            let vm = this;
            if (vm.resendAttemptCall > 0) {
                vm.textCall = "Call me again";
            } else {
                vm.textCall = "Call me with an OTP";
            }
        },
        showOTPCall() {
            let vm = this;
            return vm.countdown <= 0 && vm.resendAttempt > 1;
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
        reCallWithOTP() {
            let vm = this;
            axios.post(CONTEXT_URL + "/rest/otp/resend/voice",{
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
                    if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
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
                    setTimeout(function(){
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
                    if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
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
        onResendOTPCall() {
            let vm = this;
            vm.isLoading = true;
            if (vm.textCall === "Call me with an OTP"){
                vm.callWithOTP();
            }else{
                vm.reCallWithOTP();
            }
        },
        getRedirectUrl: function () {
            var vm = this;
            axios.get(CONTEXT_URL + "/rest/redirect").then((response) => {
                vm.redirectUri = response.data.redirectUri;
                vm.isLoading = false;
            });
        },
        handlePaste(pastedValue){
            let vm = this;
            if (vm.enabledSixboxOTP){
                // Note : enabled  6 box
                if (pastedValue.length > 5){
                    setTimeout(() => {
                        for (let i = 0; i < pastedValue.length; i++) {
                            vm.OTPData['otp' + i] = pastedValue[i];
                        }
                    }, 10);
                }
            }else{
                vm.OTPData['otp0' ] = pastedValue.substr(0,6);
            }
        },
        onPasteOTP(e) {
            let vm = this;
            vm.cleanOTP();
            let pastedValue = ((event.clipboardData || window.clipboardData).getData('text')).trim();
            pastedValue = pastedValue.replace(/\D/g, ''); // remove all character except number
            if (vm.enabledSixboxOTP === false){
                if (pastedValue.length === 6)
                    vm.handlePaste(pastedValue);
                else{
                    setTimeout(() => { 
                        $("#otp0").val(0);
                        vm.OTPData.otp0 = "";
                    }, 100);
                }
            }else{
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
            }else{
                vm.checkDisabledSubmit();
            }
        },
        togglePassword() {
            let vm = this;
            if (vm.account_info_part.showPassword)
                vm.account_info_part.showPassword = false;
            else
                vm.account_info_part.showPassword = true;
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
                    if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
                    logEvent(analytics, 'click', payload); // firebase event
                    moe(moengageConfig).track_event("click", payload); // moengage event

                }
            }).catch((error) => {
                if (error.response) {
                    if (error.response.data.description!="")
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
            return axios.post(CONTEXT_URL + "/rest/otp/request");
        },
        validateOTP() {
            let vm = this;
            let otpData = "";
            if (vm.enabledSixboxOTP){
                otpData = vm.OTPData.otp0 + "" + vm.OTPData.otp1 + "" + vm.OTPData.otp2 + "" + vm.OTPData.otp3 + "" + vm.OTPData.otp4 + "" + vm.OTPData.otp5;            
            }else{
                otpData = vm.OTPData.otp0 ;
            }
            return axios.post(CONTEXT_URL + "/rest/otp/validate", {
                otp : otpData
            });
        },
        getMyInfoUser: async function () {
            var vm = this;
                try {
                    await axios
                        .get(CONTEXT_URL + "/rest/myinfo/user")
                        .then((response) => {
                            vm.isLoading = false;
                            if (response.status === 200) {
                                var data = response.data.user;
                                vm.myInfo = response.data.isMyInfo;
                                if (vm.myInfo) {
                                    vm.isLoading = true;
                                    window.location.replace(CONTEXT_URL + "/myinfo");
                                }
                            }
                        })
                        .catch((error) => {
                            vm.isLoading = false;
                            console.log(error);
                        });
                } catch (error) {
                    console.log(error);
                }
        },
        setIntlInput: function () {
            let vm = this;
            try {   
                setTimeout(() => {
                    const input = document.querySelector("#phone");
                    if (vm.itl !== null) {
                        vm.itl.destroy();
                    }
                    let countriesData = [];
                    let countryNameLocalized = [];
                    if (vm.countries.data){
                        vm.countries.data.forEach(function (v, i) {
                            if (String(v.isoCode).length > 0) {
                                if (v.isoCode != undefined && v.cor != undefined) countriesData.push(String(v.isoCode).toLowerCase());
                                if (v.isoCode != undefined && v.cor != undefined) countryNameLocalized[String(v.isoCode).toLowerCase()] = v.cor;
                            }
                        });
                    }
                    vm.itl = window.intlTelInput(input, {
                        separateDialCode: true,
                        // autoPlaceholder: "polite",
                        preferredCountries: ["sg", "id", "my"],
                        utilsScript: "js/vendor/intl-tel-input/utils.js", // just for formatting/placeholders etc
                        onlyCountries: countriesData,
                        localizedCountries: countryNameLocalized
                    });

                    if (vm.personal_info_part.phone.code !== null) {
                        let phoneNumber = "+" + vm.personal_info_part.phone.code + " " + vm.personal_info_part.phone.number;
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

        },
        setCountries: function () {
            var vm = this;
            setTimeout(() => {
                // Set Nationalities
                let nationalitiesData = [];
                let nationalitieseLocalized = {};
                vm.nationalities.data.forEach(function (v, i) {
                    if (String(v.isoCode).length > 0) {
                        if (v.isoCode != undefined && v.nationality != undefined) nationalitiesData.push(String(v.isoCode).toLowerCase());
                        if (v.isoCode != undefined && v.nationality != undefined) nationalitieseLocalized[String(v.isoCode).toLowerCase()] = v.nationality;
                    }
                });
                $("#nationality").nationalitySelect("destroy");
                $("#nationality").nationalitySelect({
                    preferredCountries: ["sg", "id", "my"],
                    onlyCountries: nationalitiesData,
                    localizedCountries: nationalitieseLocalized
                });

                // Set Countries 
                let countriesData = [];
                let countryNameLocalized = {};
                vm.countries.data.forEach(function (v, i) {
                    if (String(v.isoCode).length > 0) {
                        if (v.isoCode != undefined && v.cor != undefined) countriesData.push(String(v.isoCode).toLowerCase());
                        if (v.isoCode != undefined && v.cor != undefined) countryNameLocalized[String(v.isoCode).toLowerCase()] = v.cor;
                    }
                });
                $("#country").countrySelect("destroy");
                $("#country").countrySelect({
                    preferredCountries: ["sg", "id", "my"],
                    onlyCountries: countriesData,
                    localizedCountries: countryNameLocalized
                });

                if (vm.personal_info_part.country) {
                    $("#country").countrySelect("setCountry", vm.personal_info_part.country.name);
                }              
                $(document).off("click", ".group-nationality .country-list .country");
                $(document).on("click", ".group-nationality .country-list .country", function (e) {
                    let code = $(this).attr("data-country-code");
                    let name = $(this).find(".country-name").html();
                    setTimeout(function () {
                        vm.personal_info_part.nationality.code = code;
                        vm.personal_info_part.nationality.name = name;
                    }, 200);
                    // $("#nationality").val(name);
                });
                $(document).off("click", ".group-country .country-list .country");
                $(document).on("click", ".group-country .country-list .country", function (e) {
                    let code = $(this).attr("data-country-code");
                    let name = $(this).find(".country-name").html();
                    setTimeout(function () {
                        vm.personal_info_part.country.code = code;
                        vm.personal_info_part.country.name = name;
                    }, 200);
                    // $("#country").val(name);
                });

                $(document).on("click", ".iti__country-list .iti__country", function (e) {
                    let code = $(this).attr("data-dial-code");
                    setTimeout(function () {
                        vm.personal_info_part.phone.code = code;
                    }, 200);
                });
            }, 1000);
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
        getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
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
            if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
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
                                            setUserProperties(analytics,{
                                                user_id_logged: vm.account_info_part.email,
                                                registered_user : "yes"
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
                                            vm.errorMessage = "Error Occured";
                                        }
                                        // redirecting [end]
                                    } catch (error) {
                                        vm.isLoading = false;
                                        vm.showErrorMessage = true;
                                        vm.errorMessage = "Error Occured";
                                    }
                                }
                            }).catch(function (error) {
                                vm.cleanOTP();
                                $("#otp0").focus();
                                vm.showErrorMessage = true;
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
                        if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
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
                        vm.isLoading = false;
                        vm.isAPIError = true;
                    });
            } catch (error) {
                vm.showErrorMessage = true;
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
        padTo2Digits(num) {
            return num.toString().padStart(2, '0');
        },
        getDefaultFormatDate() { // dd/mm/yyyy
            let date = moment(this.personal_info_part.dob).format("DD/MM/YYYY");
            return date;
        },
        getFormatDate() {
            let date = moment(this.personal_info_part.dob, "DD/MM/YYYY");
            return moment(date).format("YYYY-MM-DD");
        },
        getFormatDateToday() {
            let today = new Date();
            return [
                today.getFullYear(),
                this.padTo2Digits(today.getMonth() + 1),
                this.padTo2Digits(today.getDate()),
            ].join('-');
        },
        getFormatDateMinDate() {
            let minDate = new Date(this.minDate);
            return [
                minDate.getFullYear(),
                this.padTo2Digits(minDate.getMonth() + 1),
                this.padTo2Digits(minDate.getDate()),
            ].join('-');
        },
        commitUser() {
            let vm = this;
            try {
                vm.isLoading = false;
                return axios.post(CONTEXT_URL + "/rest/users/submit");

            } catch (error) {
                vm.showErrorMessage = true;
                vm.errorMessage = "Error Occured";
            }
        },
        onSubmit: async function () {
            var vm = this;
            vm.isSubmitted2 = true;
            if(vm.hasInputNameError("firstName")){
                vm.scrollToElement("#firstName");
                return;
            }
            if(vm.hasInputNameError("lastName")){
                vm.scrollToElement("#lastName");
                return;
            }
            if(vm.hasInputDobError()){
                vm.scrollToElement("#dateOfBirth");
                return;
            }
            if(vm.hasInputStateError('country')){
                vm.scrollToElement("#country");
                return;
            }
            if(vm.hasInputStateError('nationality')){
                vm.scrollToElement("#nationality");
                return;
            }
            if(!vm.validateNumber()){
                vm.scrollToElement("#idNumber");
                return;
            }
            if (vm.hasPhoneError()){
                vm.scrollToElement("#phone");
                return;
            }

            // #20 Event Note : Create my profile pg2: click "submit" 
            let payload = {
                source: '360 Mobile App',
                page_location: '/signup-create-profile2',
                link_id: 'signup_createprofile2_submit_b',
                content_type: 'button',
                screen_name: 'create my profile',
                content_group: 'registration_login',
                user_id: vm.account_info_part.email
            }
            if (FIREBASE_DEBUGS) console.log("Firebase Event : " +JSON.stringify( payload));
            logEvent(analytics, 'click', payload); // firebase event
            moe(moengageConfig).track_event("click", payload); // moengage event

            var idNumber = vm.personal_info_part.idNumber.toUpperCase();
            vm.trimMobileCountryCode(vm.personal_info_part.phone.code, vm.personal_info_part.phone.number); // adjust phoneNumber when sending
            var phoneNumber = vm.personal_info_part.phone.number.replace(/\s/g, "");

            // trim all input
            Object.keys(vm.personal_info_part).forEach(function (v) {
                if (typeof vm.personal_info_part[v] === "string") {
                    vm.personal_info_part[v] = (vm.personal_info_part[v].toString().trim());
                }
            });

            Object.keys(vm.account_info_part).forEach(function (v) {
                if (typeof vm.account_info_part[v] === "string") {
                    vm.account_info_part[v] = (vm.account_info_part[v].toString().trim());
                }
            });

            if (!vm.validateNumber()) return;
            if (vm.personal_info_part.phone.code === "") return;
            if (vm.personal_info_part.phone.number === "") return;
            if (vm.personal_info_part.firstName === "") return;
            if (vm.personal_info_part.lastName === "") return;
            if (!vm.isAlpha(vm.personal_info_part.firstName)) return;
            if (!vm.isAlpha(vm.personal_info_part.lastName)) return;
            if (vm.personal_info_part.dob === "") return;
            if (vm.isExceeded(vm.personal_info_part.dob) === false) return;
            if (vm.isLessThanMinDate(vm.personal_info_part.dob)) return;
            if (vm.personal_info_part.gender === "") return;
            if (vm.personal_info_part.country.name === "") return;
            if (vm.isNumeric(vm.personal_info_part.country.name)) return;
            if (vm.personal_info_part.nationality.name === "") return;
            if (vm.personal_info_part.idNumberType !== "Passport" && vm.personal_info_part.idNumberType !== "NRIC/FIN") return;
            // if (vm.isNumeric(vm.personal_info_part.nationality.name)) return;

            const employeeType = vm.personal_info_part.idNumberType === "Passport" ? "passport" : vm.personal_info_part.idNumberType === "NRIC/FIN" ? "nric" :"";
            try {
                vm.isLoading = true;
                await axios
                    .post(CONTEXT_URL + "/rest/users/store", {
                        token: vm.$refs.refToken ? vm.$refs.refToken.value : "",
                        firstName: vm.personal_info_part.firstName,
                        lastName: vm.personal_info_part.lastName.toString().length === 0 ? " " : vm.personal_info_part.lastName,
                        email: vm.account_info_part.email,
                        dob: vm.getFormatDate(),
                        id: idNumber,
                        employeeType,
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
                            vm.requestOTP().then(function (response) {
                                vm.isLoading = false;
                                if (response.status === 200) {
                                    vm.showErrorMessage = false;
                                    vm.isLoading = false;
                                    vm.successMessage = true;
                                    vm.dataStep = 3;
                                    // store personal information  part data to storage
                                    sessionStorage.setItem("personal_info_part", JSON.stringify({
                                        firstName: vm.personal_info_part.firstName,
                                        lastName: vm.personal_info_part.lastName,
                                        gender: vm.personal_info_part.gender,
                                        country: {
                                            code: vm.personal_info_part.country.code,
                                            name: vm.personal_info_part.country.name,
                                        },
                                        nationality: {
                                            code: vm.personal_info_part.nationality.code,
                                            name: vm.personal_info_part.nationality.name
                                        },
                                        phone: {
                                            code: vm.personal_info_part.phone.code,
                                            number: vm.personal_info_part.phone.number,
                                        },
                                        dob: vm.personal_info_part.dob,
                                        idNumber: vm.personal_info_part.idNumber,
                                        idNumberType: vm.personal_info_part.idNumberType
                                    }));
                                    vm.setCountDown();
                                }
                            }).catch(function (error) {
                                let failedMessage = "Failed to send message to mobile phone";
                                if (error.response.status === 400){
                                    if (error.response.data.description){
                                        if (error.response.data.description == "Invalid number"){
                                            vm.isLoading = true;
                                        }else{
                                            vm.isLoading = false;
                                            vm.showErrorMessage = true;
                                            vm.errorMessage = failedMessage;
                                            window.scrollTo(0, 0);
                                        }
                                    }else{
                                        vm.isLoading = true;
                                    }
                                }else{
                                    vm.showErrorMessage = true;
                                    vm.errorMessage = failedMessage;
                                    vm.isLoading = false;
                                    vm.isAPIError = true;
                                    window.scrollTo(0, 0);
                                }
                            });
                        }
                    })
                    .catch(function (error) {
                        if (error.response.status === 403) {
                            vm.isLoading = true;
                        } else {
                            vm.showErrorMessage = true;
                            vm.errorMessage = "Error Occured";
                            vm.isLoading = false;
                            vm.isAPIError = true;
                        }
                    })
            } catch (error) {
                vm.showErrorMessage = true;
                vm.errorMessage = "Error Occured";
                vm.isLoading = false;
                window.scrollTo(0, 0);
            }
        },
        hasEmailCorrect: function () {
            return (/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test(this.account_info_part.email);
        },
        scrollToElement(id) {
            const container = this.$el.querySelector(id);
            container.scrollIntoView({ behavior: "smooth" }); 
            container.focus()           
        },
        hasInputAllBlank: function () {
            let vm = this;

            if (vm.$refs.refToken) {
                if (vm.$refs.refToken.value == '') return true;
            }

            if (vm.account_info_part.firstName == '' ) {
                return true;
            }
            if (vm.personal_info_part.lastName == '' ) {
                return true;
            }
            if (vm.personal_info_part.gender == '' ) {
                return true;
            }
            if (vm.personal_info_part.dob == '' ) {
                return true;
            }
            if (vm.personal_info_part.country == '' ) {
                return true;
            }
            if (vm.personal_info_part.nationality == '' ) {
                return true;
            }
            if (vm.personal_info_part.idNumber == '' ) {
                return true;
            }
            if (vm.personal_info_part.phone.number == '' ) {
                return true;
            }
            
            return false;

        },
        hasInputEmailError: function () {
            let vm = this;

            if (vm.account_info_part.email == '' && vm.isSubmitted1 == true) {
                return true;
            }
            if (!vm.hasEmailCorrect() && vm.isSubmitted1 == true) {
                return true;
            }
            if (vm.isEmailExists && vm.isSubmitted1 == true) {
                return true;
            }
            return false;

        },
        hasInputEmailError: function () {
            let vm = this;

            if (vm.account_info_part.email == '' && vm.isSubmitted1 == true) {
                return true;
            }
            if (!vm.hasEmailCorrect() && vm.isSubmitted1 == true) {
                return true;
            }
            if (vm.isEmailExists && vm.isSubmitted1 == true) {
                return true;
            }
            return false;
        },
        hasInputNameError: function (name) {
            let vm = this;

            if (vm.personal_info_part[name] == '' && vm.isSubmitted2 == true) {
                return true;
            }
            if (!vm.isAlpha(vm.personal_info_part[name]) && vm.personal_info_part[name] !== '') {
                return true;
            }
            return false;
        },
        hasInputDobError: function (name) {
            let vm = this;

            if (vm.personal_info_part.dob == '' && vm.isSubmitted2 == true) {
                return true;
            }
            if (vm.isExceeded(vm.personal_info_part.dob) == false && vm.personal_info_part.dob !== '') {
                return true;
            }
            if(vm.isLessThanMinDate(vm.personal_info_part.dob)){
                return true;
            }
            return false;
        },
        hasPhoneError: function (name) {
            let vm = this;
            if (vm.itl){
                var numberType = vm.itl.getNumberType();
                var errorCode = vm.itl.getValidationError();
                if (vm.personal_info_part.phone.number == '') {
                    vm.errorMessagePhone = "This field is required.";
                    return true;
                }else if (!vm.itl.isValidNumber()) {
                    vm.errorMessagePhone = vm.errorMap[errorCode];
                    return true;
                } else if (numberType !== intlTelInputUtils.numberType.MOBILE){
                    vm.errorMessagePhone = "Please provide a valid mobile number.";
                    return true;
                }
            }
            return false;
        },
        hasInputStateError: function (name) {
            let vm = this;

            if (vm.personal_info_part[name] == '' && vm.isSubmitted2 == true) {
                return true;
            }
            if (vm.isNumeric(vm.personal_info_part[name].name && vm.personal_info_part[name].name !== '')) {               
                return true;
            }
            return false;
        },
        hasUniqueEmail: function () {
            let vm = this;
            try {
                vm.isLoading = true;
                return axios.post(CONTEXT_URL + "/rest/users/email/validate", { email: vm.account_info_part.email });

            } catch (error) {
                vm.showErrorMessage = true;
                vm.errorMessage = "Error Occured";
            }
        },
        setSessionClear: function () {
            let vm = this;
            try {
                return axios.get(CONTEXT_URL + "/rest/sessions/clear");

            } catch (error) {
                vm.showErrorMessage = true;
                vm.errorMessage = "Error Occured";
            }
        },
        submitMoengage: function () {
            let vm = this;
            try {
                vm.isLoading = true;
                return axios.post(CONTEXT_URL + "/rest/users/submit/moengage", {
                    "email": vm.account_info_part.email,
                    "consentMarketing": vm.consentMarketing,
                    "marketingSmsFlag": vm.agreeReceiveMarketing
                });
            } catch (error) {
                vm.showErrorMessage = true;
                vm.errorMessage = "Error Occured";
            }
        },
        goToStep: function (step) {
            let vm = this;
            vm.isLoading = true;
            setTimeout(() => {
                // step validation
                if (step == '2') { // if go to step 2
                    this.isSubmitted1 = true;

                    // store account part data to storage
                    sessionStorage.setItem("account_info_part", JSON.stringify({
                        email: vm.account_info_part.email,
                        password: vm.account_info_part.password,
                        showPassword: vm.account_info_part.showPassword,
                        consentMarketing: vm.consentMarketing,
                        agreeReceiveMarketing: vm.agreeReceiveMarketing
                    }));

                    let hasEmail = vm.account_info_part.email != "";
                    if (!hasEmail) {
                        return;
                    }

                    this.hasUniqueEmail().then(function (response) {
                        if (response.status === 200) {
                            vm.isLoading = true;
                            // validate email and password
                            let hasEmailCorrect = vm.hasEmailCorrect();
                            let hasPassword = vm.account_info_part.password != "";
                            let hasPasswordCorrect = vm.PasswordValidationData.has8Chars && vm.PasswordValidationData.hasNumber && vm.PasswordValidationData.hasSpecialChar && vm.PasswordValidationData.hasUppercase && vm.PasswordValidationData.hasLowercase;
                            //  let hasConsent = vm.consentMarketing;
                            //  let hasAgreed = vm.agreeReceiveMarketing;
                            if (hasEmail && hasPassword && hasPasswordCorrect && hasEmailCorrect) {
                                vm.dataStep = step;
                                window.scrollTo(0, 0); // scroll to top in order to fil form
                                $("#country").countrySelect("selectCountry", vm.personal_info_part.country.code);
                                // #21 Event Note : API success: Profile was successfully captured. 
                                let payload = {
                                    source: '360 Mobile App',
                                    link_id: 'submit_profile_success',
                                    content_type: 'api',
                                    content_group: 'registration_login',
                                    user_id: vm.account_info_part.email,
                                    registered_user: "yes"
                                };
                                if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
                                logEvent(analytics, 'api', payload); // firebase event
                                moe(moengageConfig).track_event("api", payload); // moengage event
                                // R2 :  App_First_Time_Consent_Granted - register_consent_grant_email [start]
                                moe(moengageConfig).add_unique_user_id(vm.account_info_part.email); // set unique user id
                                let payload_register_consent_grant_email = {
                                    Source: "360 Mobile App",
                                    Login_Status: false,
                                    Screen_Name: 'register_consent_grant_email',
                                    First_Session: true,
                                    Method_of_Registration: null,
                                    Method_of_LogIn: null,
                                    Email_CheckBox: vm.consentMarketing,
                                    SMS_CheckBox: vm.agreeReceiveMarketing,
                                    Country_of_Residence: null,
                                    Gender: null,
                                    Nationality: null,
                                    Event_status: true // set false due to success
                                };
                                moe(moengageConfig).track_event("App_First_Time_Consent_Granted", payload_register_consent_grant_email);
                             // R2 :  App_First_Time_Consent_Granted - register_consent_grant_email [end]
                            }
                        }
                    })
                        .catch(function (error) {
                            // #21 Event Note : API success: Profile was successfully captured. 
                            let payload = {
                                source: '360 Mobile App',
                                link_id: 'submit_profile_fail',
                                content_type: 'api',
                                content_group: 'registration_login',
                                user_id: vm.account_info_part.email
                            };
                            if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
                            logEvent(analytics, 'api', JSON.stringify(payload)); // firebase event
                            moe(moengageConfig).track_event("api", payload); // moengage event

                            // R2 :  App_First_Time_Consent_Granted - register_consent_grant_email [start]
                            let payload_register_consent_grant_email = {
                                Source: "360 Mobile App",
                                Login_Status: false,
                                Screen_Name: 'register_consent_grant_email',
                                First_Session: true,
                                Method_of_Registration: null, 
                                Method_of_LogIn : null,
                                Email_CheckBox: vm.consentMarketing,
                                SMS_CheckBox : vm.agreeReceiveMarketing,
                                Country_of_Residence : null,
                                Gender : null,
                                Nationality : null,
                                Event_status: false // set false due to catch error
                            };
                            moe(moengageConfig).track_event("App_First_Time_Consent_Granted", payload_register_consent_grant_email);
                             // R2 :  App_First_Time_Consent_Granted - register_consent_grant_email [end]

                            if (error.response){
                                if (error.response.status === 409) {
                                    window.scrollTo(0, 0);
                                    vm.isEmailExists = true;
                                } else {
                                    vm.showErrorMessage = true;
                                    vm.errorMessage = error.response.data.description;
                                    vm.isAPIError = true;
                                }
                            }else{
                                vm.showErrorMessage = true;
                                vm.errorMessage = error.response.data.description;
                                vm.isAPIError = true;
                            }
                        })
                        .then(() => {
                            vm.isLoading = false;
                        });

                }

                if (step == '3') { // if go to step 3
                    // Scroll to top after next button is clicked
                    window.scrollTo(0, 0);

                    this.isSubmitted2 = true;


                }
            }, 200);
        },
        // togglePassword: function (input) {
        //     if (input === "password") {
        //         this.showPassword = !this.showPassword;
        //     }
        // },
        otpInitiation: function () {
            let vm = this;
        }, // end of OTP Initiation
        validateNumber: function () {
            let vm = this;
            let idNumber = vm.personal_info_part.idNumber;
            let idNumberType = vm.personal_info_part.idNumberType;

            if (idNumberType === "NRIC/FIN") {
                if (idNumber.length !== 9) return false;
                var icArray = new Array(9);
                for (let i = 0; i < 9; i++) {
                    icArray[i] = idNumber.charAt(i);
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
            } else {
                // Check Passport ID
                return /^(?!^0+$)[a-zA-Z0-9']{4,10}$/.test(idNumber) ? true : false;
            }
        },
    },// end of method
});
