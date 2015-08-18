#Android Tools ( Developing... )
---------------
<br/>

Android Tools is an Android Studio plugin helping developer reducing heavy and repeat works in development.

<br>

####**Postfix Extension**

Postfix   |Comment
----------|---------
`.toast`    |generate `Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();`
...      | 



####**FindViewById Generating**
You can generate `findViewById`s from  `setContentView(R.layout.activity_login)` statement or `layoutInflater.inflate(R.layout.activity_login,parent)` statement

*	**local** :e.g. generating `TextView usernameTextView = (TextView)findViewById(R.id.textView_username)`
*	**field** :e.g generating `private TextView mUsernameTextView;` and<br/>
	`mUsernameTextView = (TextView)findViewById(R.id.textView_username);`  two parts
			


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