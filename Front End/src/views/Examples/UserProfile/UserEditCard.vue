<template>
  <div class="card">
    <div class="card-header">
      <h1>Edit Profile</h1>
    </div>
    <div class="card-body">
      <form ref="profile_form" @submit.prevent="handleProfileUpdate">
        <base-input
          label="Display name "
          prepend-icon="fas fa-user"
          placeholder="Your display name"
          v-model="user.displayName"
        />
        <base-input
          label="Location"
          prepend-icon="fas fa-map-marker-alt"
          placeholder="Location"
          v-model="user.location"
        />

        <label class="form-control-label">About me</label>
        <ckeditor
          :editor="editor"
          v-model="editorValue"
          :config="editorConfig"
        ></ckeditor>

        <div class="my-4">
          <base-button
            type="button"
            class="btn btn-sm btn-primary"
            native-type="submit"
          >
            Save Profile
          </base-button>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
import BaseInput from "@/components/Inputs/BaseInput.vue";
import BaseButton from "@/components/BaseButton.vue";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import Vue from "vue";

Vue.use(CKEditor);
export default {
  name: "UserEditCard",

  components: {
    BaseInput,
    BaseButton,
    ckeditor: CKEditor.component
  },

  props: {
    user: Object
  },

  data() {
    return {
      editorValue: "",
      editor: ClassicEditor,
      editorConfig: {
        // The configuration of the editor.
      }
    };
  },

  async created() {
    setTimeout(() => {
      this.editorValue = this.user.aboutMe;
    }, 100);
  },

  methods: {
    async handleProfileUpdate() {
      try {
        this.user.aboutMe = this.editorValue;
        await this.$store.dispatch("users/update", this.user);

        this.$notify({
          type: "success",
          message: "Profile updated successfully."
        });
        let me = JSON.stringify(this.user);
        localStorage.setItem("currentUser", me);

        window.location.replace("http://localhost:8080/user-profile");
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
<style>
.ck-editor__editable {
  min-height: 200px;
  width: auto;
}
</style>
