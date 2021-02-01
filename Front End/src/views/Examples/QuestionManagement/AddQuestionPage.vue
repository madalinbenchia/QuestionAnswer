<template>
  <div class="container-fluid mt-5">
    <div class="row">
      <div class="col-xl-12 order-xl-1">
        <card>
          <div slot="header" class="row align-items-center">
            <div class="col-8">
              <h3 class="mb-0">Ask a public question</h3>
            </div>
            <div class="col-4 text-right">
              <base-button
                @click="goBack"
                type="button"
                class="btn btn-sm btn-primary"
                icon
              >
                <span class="btn-inner--icon"
                  ><i class="ni ni-spaceship"></i
                ></span>
                <span class="btn-inner--text">Homepage</span></base-button
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
              <!-- <html-editor v-model="question.body" name="editor" /> -->
              <vue-editor
                v-model="question.Body"
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
                v-model="question.tags2"
                :add-tag-on-keys="[13, 32]"
                placeholder="e.g. (javascript, api)"
                :before-adding="toUpperCase"
              ></input-tag>

              <base-button
                type="button"
                class="btn btn-sm btn-primary mt-4 ml-0"
                native-type="submit"
              >
                Post your question
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
      question: {
        body: "",
        tags: "",
        title: "",
        owneruserid: ""
      }
    };
  },

  methods: {
    toUpperCase(v) {
      return "<" + v + ">";
    },
    goBack() {
      this.$router.push({ name: "List Questions" });
    },

    async handleSubmit() {
      try {
        this.question.tags = this.question.tags2.toString().replaceAll(",", "");
        this.user = await { ...this.$store.getters.currentUser };
        this.question.owneruserid = this.user.UserId;

        await this.$store.dispatch("questions/add", this.question);
        this.question = await this.$store.getters["questions/question"];
        console.log(this.question);
        this.$notify({
          type: "success",
          message: "Question added successfully."
        });
        this.$router.push({
          name: "View Question",
          params: { id: this.question.Id }
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
