<template>
  <div class="container-fluid mt-5">
    <div class="row">
      <div class="col-xl-12 order-xl-1">
        <card>
          <div slot="header" class="row align-items-center">
            <div class="col-8">
              <h3 class="mb-0">Edit Answer</h3>
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
              <p
                style="color: darkgray;margin-top: -10px;margin-bottom: 10px;font-style: italic;"
              >
                Please be sure to answer the question. Provide details and share
                your research!
              </p>
              <!-- <html-editor v-model="question.body" name="editor" /> -->
              <vue-editor
                v-model="answer.body"
                :editorToolbar="customToolbar"
              ></vue-editor>

              <base-button
                type="button"
                class="btn btn-sm btn-primary mt-4 ml-0"
                native-type="submit"
              >
                Edit answer
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
import { VueEditor } from "vue2-editor";
import moment from "moment";

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
      answer: {
        body: "",
        owneruserid: ""
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
        await this.$store.dispatch("answers/get", id);
        this.answer = this.$store.getters["answers/answer"];

        this.loading = false;
      } catch (error) {
        console.log(error);
        this.$notify({
          type: "danger",
          message: `Oops, something went wrong!`
        });
      }
    },

    goBack() {
      this.$router.push({
        name: "View Question",
        params: { id: this.answer.ParentId }
      });
    },

    async handleSubmit() {
      try {
        this.user = await { ...this.$store.getters.currentUser };
        this.answer.LastEditDate = moment().format();
        this.answer.LastActivityDate = moment().format();
        this.answer.CloseDate = moment().format();
        this.answer.Tags = "";
        this.answer.Title = "";

        this.answer.LastEditorDisplayName = this.user.DisplayName;
        this.answer.userId = this.user.userId;
        this.answer.tags = "tag";
        this.answer.title = "title";
        const payload = { a: this.answer, u: this.user };

        await this.$store.dispatch("answers/update", payload);
        this.answer = await this.$store.getters["answers/answer"];

        this.$notify({
          type: "success",
          message: "Answer edited successfully."
        });
        this.$router.push({
          name: "View Question",
          params: { id: this.answer.parentId }
        });
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
