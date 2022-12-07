new Vue({
    el: "#otp",
    data: {
        errorMessage: "",
        borderColor: "none",
        phone: "",
        mobileCountryCode: "",
        isLoading: false,
        isAPIError: false,
        errorMap: [
            "Please provide a valid number.",
            "Invalid country code.",
            "The number is too short.",
            "The number is too long.",
            "Please provide a valid number.",
            "Please provide a valid number.",
        ],
        itl: null,
    },
    methods: {
        handleSubmit: async function () {
            var vm = this;
            var countryIso = vm.itl.getSelectedCountryData();
            var errorCode = vm.itl.getValidationError();
            var phoneNumber = vm.phone.replace(/^0+/, "");
            if (vm.itl.isValidNumber() || errorCode === 5) {
                vm.isLoading = true;
                try {
                    await axios
                        .post(CONTEXT_URL + "/rest/otp/request").then((response) => {
                            if (response.status === 200) {
                                var parsed = JSON.stringify(countryIso);
                                localStorage.phone = phoneNumber.replace(
                                    /\s/g,
                                    ""
                                );
                                localStorage.setItem("countryIso", parsed);
                                document.location.href =
                                    CONTEXT_URL + "/mobile/validate";
                            }
                        })
                        .catch((error) => {
                            vm.isAPIError = true;
                            vm.isLoading = false;
                        })
                } catch (error) {
                    console.log(error);
                }
            } else {
                if (errorCode === -99) {
                    vm.errorMessage = vm.errorMap[0];
                    vm.borderColor = "red";
                } else {
                    vm.errorMessage = vm.errorMap[errorCode];
                    vm.borderColor = "red";
                }
            }
        },
        getMyInfoUser: async function (itl) {
            var vm = this;
            try {
                await axios
                    .get(CONTEXT_URL + "/rest/myinfo/user")
                    .then((response) => {
                        if (response.status === 200) {
                            console.log(response.data);
                            var data = response.data.user;
                            if (response.data.isMyInfo) {
                                vm.phone = data.mobileNo;
                                vm.mobileCountryCode = data.mobileCountryCode;
                                if (data.mobileNo !== "") {
                                    itl.setNumber(
                                        "+" +
                                            data.mobileCountryCode +
                                            data.mobileNo.replace(/^0+/, "")
                                    );
                                }
                            }
                        }
                    })
                    .catch(() => {});
            } catch (error) {
                console.log(error);
            }
        },
        getPhoneNumber: async function () {
            var vm = this;
            const input = document.querySelector("#phone");
            vm.itl = window.intlTelInput(input, {
                separateDialCode: true,
                preferredCountries: ["sg", "id", "my"],
                utilsScript: "../js/vendor/intl-tel-input/utils.js", // just for formatting/placeholders etc
            });
            try {
                await axios
                    .get(CONTEXT_URL + "/rest/otp/mobile")
                    .then((response) => {
                        if (response.status === 200) {
                            console.log(response.data);
                            vm.phone = response.data.mobileNo;
                            vm.mobileCountryCode =
                                response.data.mobileCountryCode;
                            vm.itl.setNumber(
                                "+" +
                                    response.data.mobileCountryCode +
                                    response.data.mobileNo.replace(/^0+/, "")
                            );
                        } else {
                            this.getMyInfoUser(vm.itl);
                        }
                    })
                    .catch((error) => {
                        if (error.response.status === 401) {
                            this.getMyInfoUser(vm.itl);
                        }
                    });
            } catch (error) {
                console.log(error);
            }
        },
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
        this.getPhoneNumber();
        
        // For cleaning temporary data save that the purpose is to keep data filled on registration form even after user open terms & condition page.
        localStorage.removeItem('previousFormData');
    },
    watch: {
        phone: function () {
            if (this.phone !== "") {
                this.errorMessage = "";
                this.borderColor = "#DFE2E6";
            }
        },
    },
});
