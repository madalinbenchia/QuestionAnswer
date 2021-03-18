<template>
  <div class="container-fluid mt-5">
    <div class="row">
      <div class="col-xl-12 order-xl-1">
        <card>
          <div slot="header" class="row align-items-center">
            <div class="col-8">
              <h3 class="mb-0">Edit Question</h3>
            </div>
            <div class="col-4 text-right">
              <base-button
                @click="goBack"
                type="button"
                class="btn btn-sm btn-primary"
                >Go Back</base-button
              >
            </div>
          </div>
          <div class="card-body">
            <form ref="profile_form" @submit.prevent="handleSubmit">
              <label for="Title" style="font-weight: 700; font-size:19px"
                >Title</label
              >
              <p
                style="color: darkgray;margin-top: -10px;margin-bottom: 10px;font-style: italic;"
              >
                Be specific and imagine you’re asking a question to another
                person
              </p>
              <base-input
                name="title"
                type="text"
                maxlength="300"
                placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
                autocomplete="off"
                v-model="question.title"
              />

              <label for="Body" style="font-weight: 700; font-size:19px"
                >Body</label
              >
              <p
                style="color: darkgray;margin-top: -10px;margin-bottom: 10px;font-style: italic;"
              >
                Include all the information someone would need to answer your
                question
              </p>
              <!-- <html-editor v-model="question.Body" name="editor" /> -->
              <vue-editor
                v-model="question.body"
                :editorToolbar="customToolbar"
              ></vue-editor>
              <label for="Title" style="font-weight: 700; font-size:19px"
                >Tags</label
              >
              <p
                style="color: darkgray;margin-top: -10px;margin-bottom: 10px;font-style: italic;"
              >
                Add up to 5 tags to describe what your question is about
              </p>
              <input-tag
                v-model="tags2"
                :add-tag-on-keys="[13, 32]"
                placeholder="e.g. (javascript, api)"
                :before-adding="toUpperCase"
              ></input-tag>

              <base-button
                type="button"
                class="btn btn-sm btn-primary mt-4 ml-0"
                native-type="submit"
              >
                Edit your question
              </base-button>
            </form>
          </div>
        </card>
      </div>
    </div>
  </div>
</template>
<script>
import BaseInput from "@/components/Inputs/BaseInput.vue";
import { Select, Option } from "element-ui";
import HtmlEditor from "@/components/Inputs/HtmlEditor";
import InputTag from "vue-input-tag";
import moment from "moment";
import { VueEditor } from "vue2-editor";

export default {
  layout: "DashboardLayout",
  components: {
    BaseInput,
    HtmlEditor,
    [Select.name]: Select,
    [Option.name]: Option,
    InputTag,
    VueEditor
  },
  data() {
    return {
      customToolbar: [
        ["bold", "italic", "underline"],
        [{ list: "ordered" }, { list: "bullet" }],
        ["image", "code"]
      ],
      user: null,
      tags2: [],
      question: {
        body: "",
        tags: "",
        title: "",
        ownerUserId: ""
      }
    };
  },

  created() {
    this.get();
  },

  methods: {
    async get() {
      try {
        this.user = await { ...this.$store.getters.currentUser };
        const id = this.$route.params.id;
        await this.$store.dispatch("questions/get", id);
        this.question = this.$store.getters["questions/question"];

        this.tags2 = this.question.tags.toString();
        this.tags2 = this.tags2.split("<");
        for (var i = 0; i < this.tags2.length - 1; i++) {
          this.tags2[i] = this.tags2[i + 1];
        }
        this.tags2.length = this.tags2.length - 1;

        for (var i = 0; i < this.tags2.length; i++) {
          this.tags2[i] = "<" + this.tags2[i];
        }

        this.loading = false;

        // const questionOwnerId = this.question.OwnerUserId;

        // await this.$store.dispatch("users/get", questionOwnerId);
        // this.questionOwner = await { ...this.$store.getters["users/user"] };

        // this.askedTimeAgo = moment(this.question.CreationDate).fromNow();
        // this.activityTimeAgo = moment(this.question.LastActivityDate).fromNow();
      } catch (error) {
        console.log(error);
        this.$notify({
          type: "danger",
          message: `Oops, something went wrong!`
        });
      }
    },
    toUpperCase(v) {
      return "<" + v + ">";
    },
    goBack() {
      this.$router.push({
        name: "View Question",
        params: { id: this.question.id }
      });
    },

    async handleSubmit() {
      try {
        this.question.tags = this.tags2.toString().replaceAll(",", "");

        this.user = await { ...this.$store.getters.currentUser };
        this.question.lastEditorDisplayName = this.user.displayName;
        this.question.closeDate = "2011-01-01T02:11:46.083";
        this.question.lastActivityDate = moment().format();
        this.question.lastEditDate = moment().format();
        this.question.userId = this.user.userId;
        const payload = { q: this.question, u: this.user };

        await this.$store.dispatch("questions/update", payload);

        this.question = await this.$store.getters["questions/question"];
        this.$notify({
          type: "success",
          message: "Question edited successfully."
        });
        this.$router.push({
          name: "View Question",
          params: { id: this.question.id }
        });
      } catch (error) {
        console.log(error);
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
      }
    }
  }
};
</script>
