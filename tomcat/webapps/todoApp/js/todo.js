// カテゴリー絞り込みイベント
document.addEventListener("change", () => {
  const checkBoxes = [...document.querySelectorAll('[name="categoryfilter"]:checked')].map(task => task.value);
  document.querySelectorAll('[data-category]').forEach(category => {
    let dataVal = category.dataset.category;
    category.classList.toggle("hidden", !checkBoxes.includes(dataVal));

    if (checkBoxes.length === 0) {
      category.classList.remove("hidden");
    }
  });
});

//　表削除イベント 
document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll(".delete-btn").forEach(btn => {
    btn.addEventListener("click", (e) => {
      const taskId = e.currentTarget.dataset.id;
      if (!taskId) return;
      if (confirm("このタスクを削除しますか？")) {
        const body = new URLSearchParams();
        body.append("no", taskId);

        fetch("/todoApp/todo", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8" },
          body: body.toString()
        })
          .then(response => {
            if (response.ok) {
              const row = document.getElementById("row-" + taskId);
              if (row) {
                row.classList.add("opacity-0", "transition", "duration-500");
                setTimeout(() => row.remove(), 500);
              }
            } else {
              alert("削除に失敗しました");
            }
          })
          .catch(() => alert("通信エラーによる削除失敗"));
      }
    });
  });
});