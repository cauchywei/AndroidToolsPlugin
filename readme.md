#Android Tools ( Developing... )
====================
<br/>

Android Tools is an Android Studio plugin helping developer reducing heavy and repeat works in development.

<br>

####**Postfix Extension**

Postfix   |Comment
----------|---------
`.toast`    |generate `Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();`
...      | 



####**FindViewById Generating**
You can generate two type `findViewById` from  `setContentView(R.layout.activity_login)` statement or `layoutInflater.inflate(R.layout.activity_login,parent)` statement

*	**local** :e.g. generating `TextView usernameTextView = (TextView)findViewById(R.id.textView_username)`
*	**field** :e.g generating `private TextView mUsernameTextView;` and<br/>
	`mUsernameTextView = (TextView)findViewById(R.id.textView_username);`  two parts

######Naming Rule
*	Activity/Fragment: `<module>_<type>` e.g. `LoginActivity`
*	Field: `m_<function>_<type>` e.g. `mUsernameTextView`
*	Local Var: `<function>_<type>` e.g. `usernameTextView`
*	Resourse Id: `<type>_<module>_<function>` e.g. `textView_login_username`

Assuming we have an Activity named `LoginActivity` which's layout file named `activiy_login.xml`

A TextView in `activity_layout.xml`

```xml
	<TextView
		android:id="@+id/textView_login_username"
		...
		/>
``` 

**The process of name-converting is as follow**

1.	Remove the type profix of view id according to tag name
	*	`textView_login_username` -> `login_username`
	*	abbreviattional profix also works e.g. 'tv_login_username' -> `login_username`
2.	Remove the module according to layout file (`activity_layout`) name
	*	`login_username` -> `username`
	*	abbreviattional profix also works e.g. `lgn_username` -> `username`
	
3.	Append ClassName according to tag name
	*	`username` -> `usernameTextView` or `mUsernameTextView`
	


License
-----------
```
Copyright (c) 2015 [cauchywei@gmail.com]

Licensed under the Apache License, Version 2.0 (the "License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```