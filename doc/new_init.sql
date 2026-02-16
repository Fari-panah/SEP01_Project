-- Reset and initialize StudyPlanner (main database)
DROP DATABASE IF EXISTS StudyPlanner;
CREATE DATABASE StudyPlanner
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE StudyPlanner;

-- Users table
CREATE TABLE Users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(100) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       confirmPassword VARCHAR(100) NOT NULL
);

-- Tasks assigned to users
CREATE TABLE Task (
                      task_id INT PRIMARY KEY AUTO_INCREMENT,
                      user_id INT NOT NULL,
                      title VARCHAR(100) NOT NULL,
                      description TEXT,
                      status VARCHAR(20),
                      dueDate DATETIME,
                      language VARCHAR(10) NOT NULL,
                      FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Stores interface text translations
CREATE TABLE translations (
                              translation_id INT PRIMARY KEY AUTO_INCREMENT,
                              translation_key VARCHAR(100) NOT NULL,
                              translation_value VARCHAR(255) NOT NULL,
                              language VARCHAR(10) NOT NULL
);

-- english
INSERT INTO translations (translation_key, translation_value, language) VALUES
-- login
('headerLogin', 'Login', 'en'),
('lblUsername', 'Username', 'en'),
('lblPassword', 'Password', 'en'),
('btnLogin', 'Login', 'en'),
('btnSignup', 'Sign up', 'en'),
('btnLanguage', 'Language list', 'en'),
('itemEnglish', 'English', 'en'),
('itemChinese', 'Chinese', 'en'),
('itemPersian', 'Persian', 'en'),
-- login errors and messages
('error.loginMessage', 'Username and Password cannot be empty', 'en'),
('success.loginMessage', 'Login successful! Welcome', 'en'),
('invalid.message', 'Invalid username or password', 'en'),
-- signup
('lblSignUp', 'Sign up', 'en'),
('lblUsername', 'Username:', 'en'),
('lblPassword', 'Password:', 'en'),
('lblConfirm', 'Confirm:', 'en'),
('btnCreateAccount', 'Create Account', 'en'),
('btnBackToLogin', 'Back to Login', 'en'),
-- signup errors and messages
('error.title', 'Error', 'en'),
('error.fillAll', 'Please fill all fields!', 'en'),
('error.usernameExists', 'Username already exists!', 'en'),
('success.title', 'Success', 'en'),
('success.accountCreated', 'Account created successfully!', 'en'),
-- first view
('lblHeaderMyTasks', 'My Tasks', 'en'),
('btnEnterTask', 'Enter Task', 'en'),
('btnTaskLists', 'Task Lists', 'en'),
-- tasklist
('btnAddTask', 'Add Task', 'en'),
('btnDeleteTask', 'Delete Task', 'en'),
('btnEditTask', 'Edit Task', 'en'),
('btnCalendarView', 'Calendar View', 'en'),
('btnDoneTasks', 'Done Tasks', 'en'),
('titleColumn', 'Title', 'en'),
('descColumn', 'Description', 'en'),
('dueDateColumn', 'Due Date', 'en'),
('statusColumn', 'Status', 'en'),
-- taskList errors and messages
('error.noTaskMessage', 'No Task Selected', 'en'),
('error.editTaskMessage', 'Please select a task in the table to edit', 'en'),
('error.deleteMessage', 'Please select a task to delete', 'en'),
('error.taskSelectMessage', 'No task selected', 'en'),
('success.taskAddMessage', 'Task has been added successfully!', 'en'),
('success.editTitle', 'Success', 'en'),
('success.taskEditMessage', 'Task has been updated successfully!', 'en'),
('success.loginMessage', 'Login successful! Welcome!', 'en'),
-- add task
('lblAddTask', 'Add New Task', 'en'),
-- edit task
('lblEditTask', 'Edit Task', 'en'),
('lblTitle', 'Title:', 'en'),
('lblDescription', 'Description:', 'en'),
('lblDueDate', 'Due Date:', 'en'),
('lblStatus', 'Status:', 'en'),
('btnCancel', 'Cancel', 'en'),
('btnSave', 'Save', 'en'),
-- No translate ERROR messages here yet

-- calendar
('lblCalendarView', '7-Day Calendar View', 'en'),
('btnBackToTaskList', 'Back to Task List', 'en'),
('prompt.title', 'Task title', 'en'),
('prompt.description', 'Task description', 'en'),
('status.TODO', 'TODO', 'en'),
('status.IN_PROGRESS', 'IN_PROGRESS', 'en'),
('status.DONE', 'DONE', 'en'),
-- calendar errors and messages
('error.validation', 'Title, Due Date, and Status are required fields.', 'en'),
('success.updated', 'Task has been updated successfully!', 'en'),
('window.taskList.title', 'Tasks', 'en'),
-- done task
('backToList', 'Back To Tasks', 'en'),
('lblDoneTask', 'Done Tasks', 'en'),
-- sign out
('btnSignOut','Sign Out','en'),
-- sign out success message
('confirm.title','Confirmation','en'),
('confirm.signOut','Are you sure you want to sign out?','en'),
('success.signOut','You have been signed out.','en');

-- finnish translations
INSERT INTO translations (translation_key, translation_value, language) VALUES
-- login
('headerLogin', 'Kirjaudu sisään', 'fi'),
('lblUsername', 'Käyttäjätunnus', 'fi'),
('lblPassword', 'Salasana', 'fi'),
('btnLogin', 'Kirjaudu', 'fi'),
('btnSignup', 'Rekisteröidy', 'fi'),
('btnLanguage', 'Kielilista', 'fi'),
('itemEnglish', 'Englanti', 'fi'),
('itemChinese', 'Kiina', 'fi'),
('itemPersian', 'Persia', 'fi'),
-- login errors and messages
('error.loginMessage', 'Käyttäjätunnus ja salasana eivät voi olla tyhjiä', 'fi'),
('success.loginMessage', 'Kirjautuminen onnistui! Tervetuloa', 'fi'),
('invalid.message', 'Virheellinen käyttäjätunnus tai salasana', 'fi'),
-- signup
('lblSignUp', 'Rekisteröidy', 'fi'),
('lblUsername', 'Käyttäjätunnus:', 'fi'),
('lblPassword', 'Salasana:', 'fi'),
('lblConfirm', 'Vahvista:', 'fi'),
('btnCreateAccount', 'Luo tili', 'fi'),
('btnBackToLogin', 'Takaisin kirjautumiseen', 'fi'),
-- signup errors and messages
('error.title', 'Virhe', 'fi'),
('error.fillAll', 'Täytä kaikki kentät!', 'fi'),
('error.usernameExists', 'Käyttäjätunnus on jo olemassa!', 'fi'),
('success.title', 'Onnistui', 'fi'),
('success.accountCreated', 'Tili luotu onnistuneesti!', 'fi'),
-- first view
('lblHeaderMyTasks', 'Omat tehtävät', 'fi'),
('btnEnterTask', 'Lisää tehtävä', 'fi'),
('btnTaskLists', 'Tehtävälistat', 'fi'),
-- task list
('btnAddTask', 'Lisää tehtävä', 'fi'),
('btnDeleteTask', 'Poista tehtävä', 'fi'),
('btnEditTask', 'Muokkaa tehtävää', 'fi'),
('btnCalendarView', 'Kalenterinäkymä', 'fi'),
('btnDoneTasks', 'Valmiit tehtävät', 'fi'),
('titleColumn', 'Otsikko', 'fi'),
('descColumn', 'Kuvaus', 'fi'),
('dueDateColumn', 'Eräpäivä', 'fi'),
('statusColumn', 'Tila', 'fi'),
-- taskList errors and messages
('error.noTaskMessage', 'Tehtävää ei valittu', 'fi'),
('error.editTaskMessage', 'Valitse muokattava tehtävä taulukosta', 'fi'),
('error.deleteMessage', 'Valitse poistettava tehtävä', 'fi'),
('error.taskSelectMessage', 'Tehtävää ei valittu', 'fi'),
('success.taskAddMessage', 'Tehtävä lisätty onnistuneesti!', 'fi'),
('success.editTitle', 'Onnistui', 'en'),
('success.taskEditMessage', 'Tehtävä on päivitetty onnistuneesti!', 'fi'),
('success.taskEditMessage', 'Tehtävä muokattu onnistuneesti!', 'fi'),
-- add task
('lblAddTask', 'Lisää uusi tehtävä', 'fi'),
-- edit task
('lblEditTask', 'Muokkaa tehtävää', 'fi'),
('lblTitle', 'Otsikko:', 'fi'),
('lblDescription', 'Kuvaus:', 'fi'),
('lblDueDate', 'Eräpäivä:', 'fi'),
('lblStatus', 'Tila:', 'fi'),
('btnCancel', 'Peruuta', 'fi'),
('btnSave', 'Tallenna', 'fi'),
-- No translate ERROR messages here yet

-- calendar
('lblCalendarView', '7 päivän kalenterinäkymä', 'fi'),
('btnBackToTaskList', 'Takaisin tehtävälistaan', 'fi'),
('prompt.title', 'Tehtävän otsikko', 'fi'),
('prompt.description', 'Tehtävän kuvaus', 'fi'),
('status.TODO', 'TEHTÄVÄ', 'fi'),
('status.IN_PROGRESS', 'KESKEN', 'fi'),
('status.DONE', 'VALMIS', 'fi'),
-- calendar errors and messages
('error.validation', 'Otsikko, eräpäivä ja tila ovat pakollisia kenttiä.', 'fi'),
('success.updated', 'Tehtävä päivitetty onnistuneesti!', 'fi'),
('window.taskList.title', 'Tehtävät', 'fi'),
-- done task
('backToList', 'Takaisin tehtäviin', 'fi'),
('lblDoneTask', 'Valmiit tehtävät', 'fi'),
-- sign out
('btnSignOut', 'Kirjaudu ulos', 'fi'),
-- sign out confirmation and success message
('confirm.title', 'Vahvistus', 'fi'),
('confirm.signOut', 'Oletko varma, että haluat kirjautua ulos?', 'fi'),
('success.signOut', 'Olet kirjautunut ulos.', 'fi');

-- chinese (simplified)
INSERT INTO translations (translation_key, translation_value, language) VALUES
-- login
('headerLogin', '登录', 'zh'),
('lblUsername', '用户名', 'zh'),
('lblPassword', '密码', 'zh'),
('btnLogin', '登录', 'zh'),
('btnSignup', '注册', 'zh'),
('btnLanguage', '语言列表', 'zh'),
('itemEnglish', '英语', 'zh'),
('itemChinese', '中文', 'zh'),
('itemPersian', '波斯语', 'zh'),
-- login errors and messages
('error.loginMessage', '用户名和密码不能为空', 'zh'),
('success.loginMessage', '登录成功！欢迎', 'zh'),
('invalid.message', '用户名或密码无效', 'zh'),
-- signup
('lblSignUp', '注册', 'zh'),
('lblUsername', '用户名:', 'zh'),
('lblPassword', '密码:', 'zh'),
('lblConfirm', '确认:', 'zh'),
('btnCreateAccount', '创建账户', 'zh'),
('btnBackToLogin', '返回登录', 'zh'),
-- signup errors and messages
('error.title', '错误', 'zh'),
('error.fillAll', '请填写所有字段！', 'zh'),
('error.usernameExists', '用户名已存在！', 'zh'),
('success.title', '成功', 'zh'),
('success.accountCreated', '账户创建成功！', 'zh'),
-- first view
('lblHeaderMyTasks', '我的任务', 'zh'),
('btnEnterTask', '输入任务', 'zh'),
('btnTaskLists', '任务列表', 'zh'),
-- task list
('btnAddTask', '添加任务', 'zh'),
('btnDeleteTask', '删除任务', 'zh'),
('btnEditTask', '编辑任务', 'zh'),
('btnCalendarView', '日历视图', 'zh'),
('btnDoneTasks', '已完成任务', 'zh'),
('titleColumn', '标题', 'zh'),
('descColumn', '描述', 'zh'),
('dueDateColumn', '截止日期', 'zh'),
('statusColumn', '状态', 'zh'),
-- taskList errors and messages
('error.noTaskMessage', '未选择任务', 'zh'),
('error.editTaskMessage', '请选择要编辑的任务', 'zh'),
('error.deleteMessage', '请选择要删除的任务', 'zh'),
('error.taskSelectMessage', '未选择任务', 'zh'),
('success.taskAddMessage', '任务已成功添加！', 'zh'),
('success.editTitle', 'Success', 'en'),
('success.taskEditMessage', '任务已成功更新！', 'zh'),
-- add task
('lblAddTask', '添加新任务', 'zh'),
-- edit task
('lblEditTask', '编辑任务', 'zh'),
('lblTitle', '标题:', 'zh'),
('lblDescription', '描述:', 'zh'),
('lblDueDate', '截止日期:', 'zh'),
('lblStatus', '状态:', 'zh'),
('btnCancel', '取消', 'zh'),
('btnSave', '保存', 'zh'),
-- No translate ERROR messages here yet

-- calendar
('lblCalendarView', '7天日历视图', 'zh'),
('btnBackToTaskList', '返回任务列表', 'zh'),
('prompt.title', '任务标题', 'zh'),
('prompt.description', '任务描述', 'zh'),
('status.TODO', '待办', 'zh'),
('status.IN_PROGRESS', '进行中', 'zh'),
('status.DONE', '已完成', 'zh'),
-- calendar errors and messages
('error.validation', '标题、截止日期和状态是必填字段。', 'zh'),
('success.updated', '任务已成功更新！', 'zh'),
('window.taskList.title', '任务', 'zh'),
-- done task
('backToList', '返回任务', 'zh'),
('lblDoneTask', '已完成任务', 'zh'),
-- sign out
('btnSignOut','退出','zh'),
-- sign out confirmation and success message
('confirm.title','确认','zh'),
('confirm.signOut','您确定要退出吗？','zh'),
('success.signOut','您已成功退出。','zh');

-- persian
INSERT INTO translations (translation_key, translation_value, language) VALUES
-- login
('headerLogin', 'ورود', 'fa'),
('lblUsername', 'نام کاربری', 'fa'),
('lblPassword', 'رمز عبور', 'fa'),
('btnLogin', 'ورود', 'fa'),
('btnSignup', 'ثبت نام', 'fa'),
('btnLanguage', 'لیست زبان‌ها', 'fa'),
('itemEnglish', 'انگلیسی', 'fa'),
('itemChinese', 'چینی', 'fa'),
('itemPersian', 'فارسی', 'fa'),
-- login errors and messages
('error.loginMessage', 'نام کاربری و رمز عبور نمی‌تواند خالی باشد', 'fa'),
('success.loginMessage', 'ورود موفقیت‌آمیز! خوش آمدید', 'fa'),
('invalid.message', 'نام کاربری یا رمز عبور نامعتبر است', 'fa'),
-- signup
('lblSignUp', 'ثبت نام', 'fa'),
('lblUsername', 'نام کاربری:', 'fa'),
('lblPassword', 'رمز عبور:', 'fa'),
('lblConfirm', 'تأیید:', 'fa'),
('btnCreateAccount', 'ایجاد حساب', 'fa'),
('btnBackToLogin', 'بازگشت به ورود', 'fa'),
-- signup errors and messages
('error.title', 'خطا', 'fa'),
('error.fillAll', 'لطفاً همه فیلدها را پر کنید!', 'fa'),
('error.usernameExists', 'نام کاربری از قبل وجود دارد!', 'fa'),
('success.title', 'موفقیت', 'fa'),
('success.accountCreated', 'حساب با موفقیت ایجاد شد!', 'fa'),
-- first view
('lblHeaderMyTasks', 'وظایف من', 'fa'),
('btnEnterTask', 'افزودن وظیفه', 'fa'),
('btnTaskLists', 'لیست وظایف', 'fa'),
-- tasklist
('btnAddTask', 'افزودن وظیفه', 'fa'),
('btnDeleteTask', 'حذف وظیفه', 'fa'),
('btnEditTask', 'ویرایش وظیفه', 'fa'),
('btnCalendarView', 'نمای تقویم', 'fa'),
('btnDoneTasks', 'وظایف انجام‌شده', 'fa'),
('titleColumn', 'عنوان', 'fa'),
('descColumn', 'توضیحات', 'fa'),
('dueDateColumn', 'تاریخ سررسید', 'fa'),
('statusColumn', 'وضعیت', 'fa'),
-- taskList errors and messages
('error.noTaskMessage', 'هیچ وظیفه‌ای انتخاب نشده است', 'fa'),
('error.editTaskMessage', 'لطفاً یک وظیفه برای ویرایش انتخاب کنید', 'fa'),
('error.deleteMessage', 'لطفاً یک وظیفه برای حذف انتخاب کنید', 'fa'),
('error.taskSelectMessage', 'هیچ وظیفه‌ای انتخاب نشده است', 'fa'),
('success.taskAddMessage', 'وظیفه با موفقیت اضافه شد!', 'fa'),
('success.taskEditMessage', 'وظیفه با موفقیت به‌روزرسانی شد!', 'fa'),
('success.title', 'موفقیت', 'fa'),
('lblAddTask', 'افزودن وظیفه جدید', 'fa'),
-- edit task
('lblEditTask', 'ویرایش وظیفه', 'fa'),
('lblTitle', 'عنوان:', 'fa'),
('lblDescription', 'توضیحات:', 'fa'),
('lblDueDate', 'تاریخ سررسید:', 'fa'),
('lblStatus', 'وضعیت:', 'fa'),
('btnCancel', 'لغو', 'fa'),
('btnSave', 'ذخیره', 'fa'),
-- No translate ERROR messages here yet

-- calendar
('lblCalendarView', 'نمای ۷ روزه تقویم', 'fa'),
('btnBackToTaskList', 'بازگشت به لیست وظایف', 'fa'),
('prompt.title', 'عنوان وظیفه', 'fa'),
('prompt.description', 'توضیحات وظیفه', 'fa'),
('status.TODO', 'برای انجام', 'fa'),
('status.IN_PROGRESS', 'در حال انجام', 'fa'),
('status.DONE', 'انجام‌شده', 'fa'),
-- calendar errors and messages
('error.validation', 'عنوان، تاریخ سررسید و وضعیت الزامی هستند.', 'fa'),
('success.updated', 'وظیفه با موفقیت به‌روزرسانی شد!', 'fa'),
('window.taskList.title', 'وظایف', 'fa'),
('backToList', 'بازگشت به وظایف', 'fa'),
('lblDoneTask', 'وظایف انجام‌شده', 'fa'),
-- sign out
('btnSignOut','خروج','fa'),
-- sign out confirmation and success message
('confirm.title','تأیید','fa'),
('confirm.signOut','آیا از خروج خود اطمینان دارید؟','fa'),
('success.signOut','خروج با موفقیت انجام شد.','fa');

-- End of StudyPlanner database initialization