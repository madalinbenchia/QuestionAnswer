<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <div class="container">
        <div class="header-body text-center mb-7">
          <div class="row justify-content-center">
            <div class="col-xl-5 col-lg-6 col-md-8 px-5">
              <h1 class="text-white">Create an account</h1>
              <p class="text-lead text-white">
                Welcome to this awesome Question-Answer Platform! Glad to have
                you here!
              </p>
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
      <!-- Table -->
      <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8">
          <div class="card bg-secondary border-0">
            <div class="card-body px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
                <small>Sign up</small>
              </div>
              <form role="form" @submit.prevent="handleSubmit()">
                <base-input
                  alternative
                  class="mb-3"
                  prepend-icon="ni ni-hat-3"
                  placeholder="Username"
                  name="Username"
                  v-model="username"
                >
                </base-input>

                <base-input
                  alternative
                  class="mb-3"
                  prepend-icon="ni ni-lock-circle-open"
                  placeholder="Password"
                  type="password"
                  name="Password"
                  v-model="password"
                >
                </base-input>
                <password
                  class="mb-3"
                  v-model="password"
                  :strength-meter-only="true"
                  @score="showScore"
                  :showStrengthMeter="false"
                />

                <base-input
                  alternative
                  class="mb-3"
                  prepend-icon="ni ni-hat-3"
                  placeholder="About Me"
                  name="About Me"
                  v-model="aboutme"
                >
                </base-input>

                <base-input
                  alternative
                  class="mb-3"
                  prepend-icon="ni ni-hat-3"
                  placeholder="Age"
                  name="Age"
                  v-model="age"
                >
                </base-input>

                <base-input
                  alternative
                  class="mb-3"
                  prepend-icon="ni ni-hat-3"
                  placeholder="Display Name"
                  name="Display Name"
                  v-model="displayname"
                >
                </base-input>

                <base-input
                  alternative
                  class="mb-3"
                  prepend-icon="ni ni-hat-3"
                  placeholder="Location"
                  name="Location"
                  v-model="location"
                >
                </base-input>

                <div class="text-muted font-italic">
                  <small
                    >password strength:
                    <template v-if="scors === 0">
                      <span class="text-danger font-weight-700">
                        very weak
                      </span>
                    </template>

                    <template v-if="scors === 1">
                      <span class="text-danger font-weight-700"> weak </span>
                    </template>

                    <template v-if="scors === 2">
                      <span class="text-warning font-weight-700"> medium </span>
                    </template>

                    <template v-if="scors === 3">
                      <span class="text-success font-weight-700"> strong </span>
                    </template>

                    <template v-if="scors === 4">
                      <span class="text-success font-weight-700">
                        very strong
                      </span>
                    </template>
                  </small>
                </div>
                <div class="row my-4">
                  <div class="col-12">
                    <base-input
                      :rules="{ required: { allowFalse: false } }"
                      name="Privacy"
                      Policy
                    >
                      <base-checkbox v-model="boolean">
                        <span class="text-muted"
                          >I agree with the
                          <a href="#!">Terms and conditions</a></span
                        >
                      </base-checkbox>
                    </base-input>
                  </div>
                </div>
                <div class="text-center">
                  <base-button type="primary" native-type="submit" class="my-4"
                    >Create account</base-button
                  >
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Password from "vue-password-strength-meter";

export default {
  components: {
    Password
  },

  data() {
    return {
      name: null,
      boolean: false,
      username: null,
      aboutme: null,
      age: null,
      displayname: null,
      location: null,
      scors: "",
      password: null
    };
  },

  methods: {
    showScore(score) {
      this.scors = score;
    },

    async handleSubmit() {
      if (!this.boolean) {
        await this.$notify({
          type: "danger",
          message: "You need to agree with our terms and conditions."
        });
        return;
      }

      const user = {
        name: this.name,
        password: this.password,
        username: this.username,
        aboutMe: "<p>" + this.aboutme + "<p>",
        age: this.age,
        displayName: this.displayname,
        location: this.location
      };

      const requestOptions = {
        headers: {
          Accept: "application/vnd.api+json"
        }
      };

      try {
        await this.$store.dispatch("register", { user, requestOptions });
        this.$notify({
          type: "success",
          message: "Successfully registered."
        });
      } catch (error) {
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
        // this.setApiValidation(error.response.data.errors);
      }
    }
  }
};
</script>
<style></style>
