new Vue({
    el: "#success",
    data: {
        isLoading: false,
        redirectUri: "",
    },
    methods: {
        getRedirectURL: function () {
            var vm = this;
            axios.get(CONTEXT_URL + "/rest/redirect").then((response) => {
                vm.redirectUri = response.data.redirectUri;
                vm.isLoading = false;
            });
        },
        gotoLogin: function () {
            window.location.href = this.redirectUri;
        },
        removeSession: async function () {
            var vm = this;
            vm.isLoading = true;
            try {
                await axios
                    .get(CONTEXT_URL + "/rest/sessions/clear")
                    .then((response) => {
                        localStorage.removeItem("countryIso");
                        localStorage.removeItem("phone");
                        console.log(response);
                        return true;
                    })
                    .catch((error) => {
                        console.log(error.response);
                    });
            } catch (error) {
                console.log(error);
            }
        },
    },
    mounted: function () {
        this.getRedirectURL();
        this.removeSession();
    },
});
