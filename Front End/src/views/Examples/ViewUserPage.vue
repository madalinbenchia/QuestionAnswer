<template>
  <div>
    <div class="header pb-6 d-flex align-items-center profile-header">
      <!-- Mask -->
      <span class="mask bg-gradient-default opacity-8"></span>
      <!-- Header container -->
      <div class="container-fluid d-flex align-items-center">
        <div class="row">
          <div class="col-lg-7 col-md-10">
            <div style="white-space:nowrap;">
              <h1 class="display-2 text-white">
                {{ this.user.DisplayName }}'s Profile

                <img
                  v-if="this.badge == 'Bronze'"
                  src="/img/bronze.png"
                  alt=""
                  style="height: 200px; width: 180px"
                />
                <img
                  v-if="this.badge == 'Silver'"
                  src="/img/silver.png"
                  alt=""
                  style="height: 200px; width: 180px"
                />
                <img
                  v-if="this.badge == 'Gold'"
                  src="/img/gold.png"
                  alt=""
                  style="height: 200px; width: 180px"
                />
                <img
                  v-if="this.badge == 'Diamond'"
                  src="/img/diamond.png"
                  alt=""
                  style="height: 200px; width: 180px"
                />
                <img
                  v-if="this.badge == 'Platinum'"
                  src="/img/platinum.png"
                  alt=""
                  style="height: 200px; width: 180px"
                />
              </h1>
            </div>
            <p class="text-white mt-0 mb-5">
              This is {{ this.user.DisplayName }}'s profile page. You can see
              their progress and also you can view some
              {{ this.user.DisplayName }}'s information here
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="col-xl-12 order-xl-1" style="margin-top:-80px">
      <div class="row">
        <div class="col-lg-4">
          <card gradient="info" class="border-0">
            <div class="row">
              <div class="col">
                <h5
                  class="card-title text-uppercase text-muted mb-0 text-white"
                >
                  Total UpVotes
                </h5>
                <span class="h2 font-weight-bold mb-0 text-white">{{
                  user.UpVotes
                }}</span>
              </div>
              <div class="col-auto">
                <div
                  class="icon icon-shape bg-white text-dark rounded-circle shadow"
                >
                  <i class="far fa-thumbs-up"></i>
                </div>
              </div>
            </div>
            <p class="mt-3 mb-0 text-sm">
              <span class="text-white mr-2"
                ><i class="fas fa-check"></i> {{ UpVotePercent }} %
              </span>
              <span class="text-nowrap text-light">of total votes</span>
            </p>
          </card>
        </div>
        <div class="col-lg-4">
          <card gradient="danger" class="border-0">
            <div class="row">
              <div class="col">
                <h5
                  class="card-title text-uppercase text-muted mb-0 text-white"
                >
                  Total downvotes
                </h5>
                <span class="h2 font-weight-bold mb-0 text-white">{{
                  user.DownVotes
                }}</span>
              </div>
              <div class="col-auto">
                <div
                  class="icon icon-shape bg-white text-dark rounded-circle shadow"
                >
                  <i class="far fa-thumbs-down"></i>
                </div>
              </div>
            </div>
            <p class="mt-3 mb-0 text-sm">
              <span class="text-white mr-2"
                ><i class="fas fa-check"></i> {{ DownVotePercent }} %
              </span>
              <span class="text-nowrap text-light">of total votes</span>
            </p>
          </card>
        </div>
        <div class="col-lg-4">
          <card gradient="primary" class="border-0">
            <div class="row">
              <div class="col">
                <h5
                  class="card-title text-uppercase text-muted mb-0 text-white"
                >
                  Reputaton
                </h5>
                <span class="h2 font-weight-bold mb-0 text-white">{{
                  user.Reputation
                }}</span>
              </div>
              <div class="col-auto">
                <div
                  class="icon icon-shape bg-white text-dark rounded-circle shadow"
                >
                  <i class="fas fa-star"></i>
                </div>
              </div>
            </div>
            <p class="mt-3 mb-0 text-sm">
              <span class="text-white mr-2"
                ><i class="fas fa-check"></i> points
              </span>
              <span class="text-nowrap text-light">earned</span>
            </p>
          </card>
        </div>
      </div>
    </div>

    <div class="card-body">
      <form ref="profile_form" @submit.prevent="handleProfileUpdate">
        <base-input
          label="Display name "
          prepend-icon="fas fa-user"
          placeholder="Your display name"
          v-model="user.DisplayName"
          disabled
        />
        <base-input
          label="Location"
          prepend-icon="fas fa-map-marker-alt"
          placeholder="User did not set their location "
          v-model="user.Location"
          disabled
        />

        <label class="form-control-label">About me</label>
        <ckeditor
          :editor="editor"
          v-model="editorValue"
          :config="editorConfig"
          disabled
        ></ckeditor>
      </form>
    </div>
  </div>
</template>
<script>
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import Vue from "vue";

Vue.use(CKEditor);
export default {
  components: {
    ckeditor: CKEditor.component
  },
  layout: "DashboardLayout",

  data() {
    return {
      badge: "",
      user: {
        AboutMe: null,
        Age: null,
        DisplayName: null,
        Email: null,
        Username: null
      },
      editorValue: "",
      editor: ClassicEditor,
      editorConfig: {
        // The configuration of the editor.
      }
    };
  },
  computed: {
    UpVotePercent() {
      if (this.user.UpVotes == 0) return 0;
      else
        return Math.round(
          (this.user.UpVotes * 100) / (this.user.DownVotes + this.user.UpVotes)
        );
    },
    DownVotePercent() {
      if (this.user.DownVotes == 0) return 0;
      else
        return Math.round(
          (this.user.DownVotes * 100) /
            (this.user.DownVotes + this.user.UpVotes)
        );
    }
  },
  created() {
    this.get();
  },

  methods: {
    async get() {
      try {
        const id = this.$route.params.id;
        await this.$store.dispatch("users/getdetails", id);
        this.user = this.$store.getters["users/user"];
        this.editorValue = this.user.AboutMe;
        if (this.user.Badges.length > 0) {
          this.badge = this.user.Badges[this.user.Badges.length - 1].Name;
          console.log(this.badge);
        }
      } catch (error) {
        console.log(error);
        this.$notify({
          type: "danger",
          message: `Oops, something went wrong!`
        });
      }
    }
  }
};
</script>
<style>
.profile-header {
  background-image: url(/img/theme/profile-cover2.jpg);
  background-size: cover;
  background-position: center top;
  min-height: 500px;
}
</style>
