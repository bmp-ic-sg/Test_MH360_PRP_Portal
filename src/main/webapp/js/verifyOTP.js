new Vue({
    el: "#verifyOTP",
    data: {
        otp: "",
        isVerifying: true,
        isLoadingVerify: false,
        isLoadingResend: false,
        isInvalidOTP: false,
        isCountdown: true,
        isResendAllowed: true,
        timeLimit: 90347,
        showHelp: false
    },
    methods: {
        handleSubmit: async function () {
            var vm = this;
            vm.isInvalidOTP = false;
            vm.isResendAllowed = false;
            vm.isVerifying = true;
            vm.isLoadingVerify = true;
            try {
                await axios
                    .post(CONTEXT_URL + "/rest/otp/validate", {
                        otp: vm.otp,
                    })
                    .then(function (response) {
                        if (response.status === 200) {
                            document.location.href = CONTEXT_URL + "/register";
                        }
                    })
                    .catch(function (error) {
                        const inputs =
                            document.querySelectorAll("#OTPInput > *[id]");

                        for (let i = 0; i < inputs.length; i++) {
                            inputs[i].value = "";
                        }

                        document.getElementById("otp-field0").focus();
                        if (error.response.status === 400) {
                            vm.isInvalidOTP = true;
                        }
                    })
                    .then(() => {
                        vm.isLoadingVerify = false;
                        if (!vm.isCountdown) {
                            vm.isResendAllowed = true;
                            vm.isLoadingResend = false;
                        }
                    });
            } catch (error) {
                console.log(error);
            }
        },
        resendOTP: async function () {
            var vm = this;
            const inputs = document.querySelectorAll("#OTPInput > *[id]");
            vm.isInvalidOTP = false;
            vm.isLoadingResend = true;
            try {
                await axios
                    .post(CONTEXT_URL + "/rest/otp/resend")
                    .then(function (response) {
                        if (response.status === 200) {
                            $(".toast-success").toast("show");

                            for (let i = 0; i < inputs.length; i++) {
                                inputs[i].value = "";
                            }

                            document.getElementById("otp-field0").focus();
                        } else {
                            $(".toast-danger").toast("show");
                        }
                    })
                    .catch(function (error) {
                        $(".toast-danger").toast("show");
                        console.log(error);
                    })
                    .then(() => {
                        vm.isLoadingResend = false;
                    });
            } catch (error) {
                $(".toast-danger").toast("show");
                console.log(error);
            }
        },
        setupOTPField: function () {
            var vm = this;
            const $otp_length = 6;
            const element = document.getElementById("OTPInput");

            for (let i = 0; i < $otp_length; i++) {
                let inputField = document.createElement("input"); // Creates a new input element
                inputField.className =
                    "m-2 p-1 form-control-otp text-center fw-bold";
                inputField.setAttribute(
                    "onkeypress",
                    "return isNumberKey(event)"
                );
                inputField.setAttribute("type", "text");
                inputField.setAttribute("autocomplete", "none");
                inputField.setAttribute("inputmode", "tel");
                inputField.id = "otp-field" + i;
                inputField.maxLength = 1; // Sets individual field length to 1 char
                element.appendChild(inputField); // Adds the input field to the parent div (OTPInput)
            }
            document.getElementById("otp-field0").focus();

            /* 	This is for switching back and forth the input box for user experience */
            const inputs = document.querySelectorAll("#OTPInput > *[id]");
            for (let i = 0; i < inputs.length; i++) {
                inputs[i].addEventListener("keydown", function (event) {
                    if (event.key === "Backspace") {
                        inputs[i].value = "";
                        if (i !== 0) inputs[i - 1].focus();
                        vm.isVerifying = true;
                    } else if (event.key === "ArrowLeft" && i !== 0) {
                        inputs[i - 1].focus();
                    } else if (
                        event.key === "ArrowRight" &&
                        i !== inputs.length - 1
                    ) {
                        inputs[i + 1].focus();
                    }
                });
                inputs[i].addEventListener("input", function () {
                    inputs[i].value = inputs[i].value;
                    var compiledOtp = "";

                    for (let i = 0; i < inputs.length; i++) {
                        compiledOtp += inputs[i].value;
                    }

                    if (compiledOtp.length === 6) {
                        vm.isVerifying = false;
                        vm.otp = compiledOtp;
                        vm.handleSubmit();
                    } else {
                        vm.isVerifying = true;
                    }

                    if (inputs[i].value !== "" && i !== inputs.length - 1) {
                        inputs[i + 1].focus();
                    }
                });
            }
        },
        toggleHelp: function() {
            var vm = this;
            vm.showHelp = !vm.showHelp
            if(vm.showHelp){
                setTimeout(function(){
                    $(".uss-message-tips").removeClass("d-none");
                });
            }
        }
    },
    created: function () {
        window.addEventListener("pageshow", function (event) {
            var historyTraversal =
                event.persisted ||
                (typeof window.performance != "undefined" &&
                    window.performance.getEntriesByType("navigation")[0]
                        .type === "back_forward");
            if (historyTraversal) {
                // Handle page restore.
                window.location.reload();
            }
        });
    },
    mounted: function () {
        this.setupOTPField();
    },
});
