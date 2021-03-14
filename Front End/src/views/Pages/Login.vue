<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <div class="container">
        <div class="header-body text-center mb-7">
          <div class="row justify-content-center">
            <div class="text-center" style="margin-bottom: 5px">
              <h1 class="text-white mb-4">
                Welcome to our Qustion-Answer Platform
              </h1>
            </div>

            <div class="text-white">
              <h3 class="text-white">
                <strong>
                  Glad to have you here! We are a nice community of responsible
                  users and you can always count on us to find the right answers
                  for your questions or unclearities</strong
                >
              </h3>
            </div>
          </div>
        </div>
      </div>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg
          x="0"
          y="0"
          viewBox="0 0 2560 100"
          preserveAspectRatio="none"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
        >
          <polygon
            class="fill-default"
            points="2560 0 2560 100 0 100"
          ></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <div class="container mt--8 pb-5">
      <div class="row justify-content-center">
        <div class="col-lg-5 col-md-7">
          <div class="card bg-secondary border-0 mb-0">
            <div class="card-body px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
                <small>Sign in with credentials</small>
              </div>
              <form role="form" @submit.prevent="handleSubmit()">
                <base-input
                  alternative
                  class="mb-3"
                  name="username"
                  prepend-icon="ni ni-email-83"
                  placeholder="Username"
                  v-model="username"
                />

                <base-input
                  alternative
                  class="mb-3"
                  name="Password"
                  prepend-icon="ni ni-lock-circle-open"
                  type="password"
                  placeholder="Password"
                  v-model="password"
                >
                </base-input>

                <div class="text-center">
                  <base-button type="primary" native-type="submit" class="my-4"
                    >Sign in</base-button
                  >
                </div>
              </form>
            </div>
          </div>
          <div class="row mt-3">
            <div class="col-6">
              <a href="#" class="text-light"><small>Forgot password?</small></a>
            </div>
            <div class="col-6 text-right">
              <a href="/register" class="text-light"
                ><small>Create new account</small></a
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data: () => ({
    username: "madauser",
    password: "secret123"
  }),
  computed: {
    isAuthenticated: function() {
      return this.$store.getters["isAuthenticated"];
    }
  },
  methods: {
    async handleSubmit() {
      const user = {
        username: this.username,
        password: this.password
      };
      const requestOptions = {
        headers: {
          Accept: "application/vnd.api+json"
        }
      };

      try {
        //   console.log("blogin");
        //  console.log(this.isAuthenticated);

        await this.$store.dispatch("login", { user, requestOptions });

        //   console.log("alogin");
        //   console.log(this.isAuthenticated);
      } catch (error) {
        console.log(error.message);
        this.$notify({
          type: "danger",
          message: "Invalid credentials!"
        });
        // this.setApiValidation(error.response.data.errors);
      }
    }
  }
};
</script>
