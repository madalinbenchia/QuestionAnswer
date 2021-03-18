<template>
  <base-nav
    container-classes="container-fluid"
    class="navbar-top border-bottom navbar-expand"
    :class="{ 'bg-success navbar-dark': type === 'default' }"
  >
    <!-- Search form -->
    <form
      class="navbar-search form-inline mr-sm-3"
      :class="{
        'navbar-search-light': type === 'default',
        'navbar-search-dark': type === 'light'
      }"
      id="navbar-search-main"
      @submit.prevent="sendMessage"
    >
      <div class="form-group mb-0">
        <div class="input-group input-group-alternative input-group-merge">
          <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-search"></i></span>
          </div>
          <input
            class="form-control"
            placeholder="Search..."
            type="text"
            v-model="searchQuery"
            required
            pattern=".{2,}"
          />
        </div>
      </div>
      <button
        type="button"
        class="close"
        data-action="search-close"
        data-target="#navbar-search-main"
        aria-label="Close"
      >
        <span aria-hidden="true">×</span>
      </button>
    </form>

    <ul class="navbar-nav align-items-center  ml-md-auto">
      <base-button
        class="mt-2 mr-4"
        type="primary"
        icon
        size="sm"
        @click="homepagelink()"
      >
        <span class="btn-inner--icon"><i class="ni ni-spaceship"></i></span>
        <span class="btn-inner--text">Homepage</span>
      </base-button>
      <base-button
        class="mt-2 mr-4"
        type="info"
        icon
        size="sm"
        @click="addQuestion()"
      >
        <span class="btn-inner--icon"><i class="far fa-clone"></i></span>
        <span class="btn-inner--text">Ask Question</span>
      </base-button>
      <base-dropdown
        menu-on-right
        class="nav-item"
        tag="li"
        title-tag="a"
        title-classes="nav-link pr-0"
      >
        <a href="#" class="nav-link pr-0" @click.prevent slot="title-container">
          <div class="media align-items-center">
            <div
              class="media-body d-none d-lg-block"
              style="white-space:nowrap"
            >
              <i class="fas fa-user-alt mr-4 mt-0" style="font-size:150%"></i>

              <span class="mt-0 font-weight-bold">{{
                this.user.displayName
              }}</span>
            </div>
          </div>
        </a>

        <template>
          <div class="dropdown-header noti-title">
            <h6 class="text-overflow m-0">Welcome!</h6>
          </div>
          <a href="/user-profile" class="dropdown-item">
            <i class="ni ni-single-02"></i>
            <span>My profile</span>
          </a>
          <div class="dropdown-divider"></div>
          <a @click.prevent="logout()" to="" class="dropdown-item">
            <i class="ni ni-user-run"></i>
            <span>Logout</span>
          </a>
        </template>
      </base-dropdown>
    </ul>
  </base-nav>
</template>
<script>
import { BaseNav } from "@/components";
import store from "@/store";

export default {
  components: {
    BaseNav
  },
  props: {
    type: {
      type: String,
      default: "default", // default|light
      description:
        "Look of the dashboard navbar. Default (Green) or light (gray)"
    }
  },
  async created() {
    this.user = await { ...store.getters.currentUser };
  },
  data() {
    return {
      activeNotifications: false,
      showMenu: false,
      searchModalVisible: false,
      searchQuery: "",
      title: "Profile",
      avatar: null,
      user: ""
    };
  },
  methods: {
    sendMessage() {
      this.$router.push({
        name: "Search",
        params: { id: this.searchQuery }
      });
      this.searchQuery = "";
    },
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    toggleNotificationDropDown() {
      this.activeNotifications = !this.activeNotifications;
    },
    closeDropDown() {
      this.activeNotifications = false;
    },
    toggleSidebar() {
      this.$sidebar.displaySidebar(!this.$sidebar.showSidebar);
    },
    hideSidebar() {
      this.$sidebar.displaySidebar(false);
    },
    addQuestion() {
      this.$router.push({ name: "Add Question" });
    },
    homepagelink() {
      this.$router.push({ name: "List Questions" });
    },
    async logout() {
      try {
        console.log("blogout");
        console.log(store.getters.isAuthenticated);
        console.log(store.getters.currentUser);

        this.$store.dispatch("logout");
        console.log("alogout");
        console.log(store.getters.currentUser);

        console.log(store.getters.isAuthenticated);
      } catch (error) {
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
      }
    }
  }
};
</script>
