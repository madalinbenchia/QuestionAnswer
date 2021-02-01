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
                Hello {{ this.user.DisplayName }}
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
              This is your profile page. You can see the progress you've made
              and also you can edit your personal information here
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
    <div class="row">
      <div class="col-xl-12 order-xl-1">
        <div>
          <user-edit-card :user="user" />
        </div>
        <div class="mt-5">
          <user-password-card :user="user" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import UserEditCard from "@/views/Examples/UserProfile/UserEditCard.vue";
import UserPasswordCard from "@/views/Examples/UserProfile/UserPasswordCard.vue";
import store from "@/store";

export default {
  layout: "DashboardLayout",

  components: {
    UserEditCard,
    UserPasswordCard
  },

  data() {
    return {
      user: {
        AboutMe: null,
        Age: null,
        DisplayName: null,
        Email: null,
        Username: null
      },
      badge: "",
      userDetails: ""
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
    this.getProfile();
  },

  methods: {
    async getProfile() {
      this.user = await { ...store.getters.currentUser };
      await this.$store.dispatch("users/getdetails", this.user.UserId);
      this.userDetails = this.$store.getters["users/user"];
      if (this.userDetails.Badges.length > 0) {
        this.badge = this.userDetails.Badges[
          this.userDetails.Badges.length - 1
        ].Name;
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
