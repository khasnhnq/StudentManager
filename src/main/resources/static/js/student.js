function deleteStudent(id) {
  if (!confirm("Xóa sinh viên này?")) return;

  fetch(`/api/students/${id}`, {
    method: "DELETE",
  }).then(() => location.reload());
}
