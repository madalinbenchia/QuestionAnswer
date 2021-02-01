<template>
  <div class="media media-comment">
    <div class="media-body">
      <div class="media-comment-text">
        <h6
          class="h5 mt-0"
          style="cursor:pointer"
          @click="viewUser(user.UserId)"
        >
          {{ user.DisplayName }}
        </h6>
        <p class="text-sm lh-160" v-html="text"></p>
        <p
          class=""
          style="font-size:smaller; margin-top: -50px; margin-bottom: 0px;margin-left:82%; color:gray"
        >
          added on <i>{{ format_date(cdate) }}</i>
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import moment from "moment";
export default {
  name: "comment",
  props: {
    cdate: {
      type: String,
      default: ""
    },
    text: {
      type: String,
      default:
        "Cras sit amet nibh libero nulla vel metus scelerisque ante sollicitudin. Cras purus odio vestibulum in vulputate viverra turpis."
    },
    uid: {
      type: Number,
      default: 1
    }
  },

  data() {
    return {
      user: ""
    };
  },

  created() {
    this.get();
  },

  methods: {
    async get() {
      await this.$store.dispatch("users/get", this.uid);
      this.user = await { ...this.$store.getters["users/user"] };
    },
    format_date(value) {
      if (value) {
        return moment(String(value)).format("MMM D 'YY, h:mm");
      }
    },
    async viewUser(Id) {
      this.$router.push({
        name: "View User",
        params: { id: Id }
      });
    }
  }
};
</script>
<style></style>
