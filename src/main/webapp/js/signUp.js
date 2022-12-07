import { analytics, FIREBASE_DEBUGS, moengageConfig } from "./main.js";
import { logEvent, setUserProperties, setUserId } from "https://www.gstatic.com/firebasejs/9.8.4/firebase-analytics.js";
new Vue({
    el: "#signUpV2",
    data: {
        isLoading: false,
        redirectUri: "",
    },
    methods: {
        getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
        },
        pageview: function () {
            let vm = this;
            let device_id = vm.getCookie("_ga");
            // #13 Event Note : Sign up: Create account  
            setUserProperties(analytics, {
                device_id: device_id
            });
            setUserId(analytics, null);
            // #13 Event Note : Sign up: Create account
            let payload = {
                source: '360 Mobile App',
                page_location: '/signup-create-account',
                screen_name: 'create account',
                content_group: 'registration_login'
            };
            if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
            logEvent(analytics, 'page_view', payload); // firebase event
            moe(moengageConfig).track_event("page_view", payload); // moengage event

        },
        goToWhatsapp: function () {

            //#16 Event Note : click Whatsapp chat button
            let payload = {
                source: '360 Mobile App',
                page_location: '/signup-create-account',
                link_id: 'signup_whatsapphelp_b',
                content_type: 'button',
                screen_name: 'create account',
                content_group: 'registration_login',
                item_name: "whatsapp +65 8799 7787"
            };
            if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
            logEvent(analytics, 'whatsapp_helpchat', payload); // firebase event
            moe(moengageConfig).track_event("whatsapp_helpchat", payload); // moengage event

            setTimeout(() => {
                location = "https://wa.me/6587997787";
            }, 1000);

        },
        signUp: function (method) {
            let vm = this;
            vm.isLoading = true;
            if (method === "myInfo") {
                vm.isLoading = true;
                // #29 Event Note : click "Sign up with Singpass"
                let payload = {
                    source: '360 Mobile App',
                    page_location: '/signup-create-account',
                    link_id: 'signup_singpass_b',
                    content_type: 'button',
                    screen_name: 'create account',
                    content_group: 'registration_login'
                };
                if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
                logEvent(analytics, 'click', payload); // firebase event
                moe(moengageConfig).track_event("click", payload); // moengage event

                setTimeout(() => {
                    location = CONTEXT_URL + "/rest/myinfo/authorize";
                }, 1000);

            } else { // sign up manually
                //#14 Event Note : click "Sign up with an email"
                let payload = {
                    source: '360 Mobile App',
                    page_location: '/signup-create-account',
                    link_id: 'signup_manual_email_b',
                    content_type: 'button',
                    screen_name: 'create account',
                    content_group: 'registration_login'
                };
                if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
                logEvent(analytics, 'click', payload); // firebase event
                moe(moengageConfig).track_event("click", payload); // moengage event

                setTimeout(() => {
                    location = CONTEXT_URL + "/register";
                }, 1000);
            }
        },
        getRedirectUrl: function () {
            var vm = this;
            axios.get(CONTEXT_URL + "/rest/redirect").then((response) => {
                vm.redirectUri = response.data.redirectUri;
                vm.isLoading = false;
            });
        },
        gotoLogin: function () {
            let vm = this;
            vm.isLoading = true;
            // #15 Event Note : click "Login now"
            let payload = {
                source: '360 Mobile App',
                page_location: '/signup-create-account',
                link_id: 'signup_login_now',
                content_type: 'link',
                screen_name: 'create account',
                content_group: 'registration_login'
            };
            if (FIREBASE_DEBUGS) console.log("Firebase Event : " + JSON.stringify(payload));
            logEvent(analytics, 'click', payload); // firebase event
            moe(moengageConfig).track_event("click", payload); // moengage event

            setTimeout(() => {
                window.location.href = vm.redirectUri;
            }, 1000);
        },
        onResetSession() {
            return axios.get(CONTEXT_URL + "/rest/sessions/reset");
        },
        removeSession: async function () {
            var vm = this;
            vm.isLoading = true;
            try {
                await axios
                    .get(CONTEXT_URL + "/rest/sessions/clear")
                    .then(() => {
                        sessionStorage.removeItem("personal_info_part");
                        sessionStorage.removeItem("account_info_part");
                        console.info("session is cleared");
                        return true;
                    })
                    .catch((error) => {
                        console.log(error.response);
                    })
                    .then(() => {
                        vm.isLoading = false;
                    });
            } catch (error) {
                console.log(error);
            }
        },
    },
    mounted: function () {
        this.pageview();
        this.removeSession();
        this.getRedirectUrl();
        this.onResetSession();
    },
});
