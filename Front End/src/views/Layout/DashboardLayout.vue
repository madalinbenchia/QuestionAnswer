<template>
  <div class="wrapper">
    <notifications></notifications>
    <side-bar>
      <template slot="links">
        <sidebar-item
          class="margin-top:125px"
          :link="{
            name: 'User Profile',
            path: '/user-profile',
            icon: 'ni ni-single-02 text-red'
          }"
        />

        <sidebar-item
          :link="{
            name: 'Home',
            path: '/question-management/list-questions',
            icon: 'ni ni-spaceship text-blue'
          }"
        />
        <sidebar-item
          :link="{
            name: 'Ask a question',
            path: '/question-management/add-question',
            icon: 'fas fa-question text-orange'
          }"
        />

        <sidebar-item
          :link="{
            name: 'Calendar',
            icon: 'ni ni-calendar-grid-58 text-green',
            path: '/calendar'
          }"
        >
        </sidebar-item>

        <hr
          style="
            margin-top:100px;
            margin-bottom:100px;
            border: 0;
            border-top: 1px solid rgba(0, 0, 0, 0.1);
            min-width: 80%;
            overflow: visible;
            box-sizing: content-box;
            height: 0;
          "
        />

        <sidebar-item
          :link="{
            name: 'FMI',
            icon: 'fas fa-university mr-4',
            target: '_blank',
            isRoute: true,
            path: 'https://fmi.unibuc.ro/'
          }"
          class="btn btn-danger"
          style="padding: 11px 0px"
        ></sidebar-item>
        <sidebar-item
          :link="{
            name: 'About us',
            icon: 'fas fa-user-graduate mr-4',
            target: '_blank',
            isRoute: true,
            path: '#'
          }"
          class="btn btn-info"
          style="padding: 11px 0px"
        ></sidebar-item>
        <sidebar-item
          :link="{
            name: 'Star us on Github',
            icon: 'fab fa-github mr-4',
            target: '_blank',
            isRoute: true,
            path: '#'
          }"
          class="btn btn-dark"
          style="padding: 11px 0px"
        ></sidebar-item>
        <sidebar-item
          :link="{
            name: 'Documentation',
            icon: 'fas fa-file-alt mr-4',
            target: '_blank',
            isRoute: true,
            path: '#'
          }"
          class="btn btn-neutral"
          style="padding: 11px 0px"
        ></sidebar-item>
      </template>
    </side-bar>
    <div class="main-content">
      <dashboard-navbar :type="$route.meta.navbarType"></dashboard-navbar>

      <div @click="$sidebar.displaySidebar(false)">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
      </div>
      <content-footer v-if="!$route.meta.hideFooter"></content-footer>
    </div>
  </div>
</template>
<script>
/* eslint-disable no-new */
import PerfectScrollbar from "perfect-scrollbar";
import "perfect-scrollbar/css/perfect-scrollbar.css";

function hasElement(className) {
  return document.getElementsByClassName(className).length > 0;
}

function initScrollbar(className) {
  if (hasElement(className)) {
    new PerfectScrollbar(`.${className}`);
  } else {
    // try to init it later in case this component is loaded async
    setTimeout(() => {
      initScrollbar(className);
    }, 100);
  }
}

import DashboardNavbar from "./DashboardNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import { FadeTransition } from "vue2-transitions";
import Vuex from "vuex";

export default {
  components: {
    DashboardNavbar,
    ContentFooter,
    FadeTransition
  },
  data() {
    return {
      roles: []
    };
  },
  methods: {
    initScrollbar() {
      let isWindows = navigator.platform.startsWith("Win");
      if (isWindows) {
        initScrollbar("sidenav");
      }
    }
  },
  mounted() {
    this.initScrollbar(); // this.$store.dispatch("profile/me");
  }
  // computed: {
  //   ...Vuex.mapState({
  //     me: state => state.profile.me
  //   })
  // },
  // watch: {
  //   me: {
  //     handler: function(val) {
  //       this.roles = val.roles.map(r => r.name);
  //     },
  //     deep: true
  //   }
  // }
};
</script>
<style lang="scss"></style>
