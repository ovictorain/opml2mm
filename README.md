# opml2mm
## English:
### Background
  Recently, I began to use mind mapping.However， I haven't found a FREE tool that have both Desktop Edition and Mobile App. So I use FreeMind on Windows, and Mindly on Mobile Device. <br />
  Unfortunately, FreeMind use .mm file and cant't export .opml file, the formate Mindly used in import. So I set up this project to conver fbetween .opml and .mm.
### Pre-requisites
  JDK1.8.
### Running the jar
  Execute the script as follows from the terminal:  <br />
  `java -jar convertOpmlMm.jar xxx.mm` <br />
  Ensure that you have put the file in files/,  a folder as a sibling of the convertOpmlMm.jar.The suffix can be either .opml or .mm, the code can verify and generate corresponding file.
### Import the project
  `git clone git@github.com:ovictorain/opml2mm.git` <br />
  Then import to your eclipse. (It has not been tested with IntelliJ IDEA and NetBean).
### Others
  When Mindly(ver1.14) export .opml, it'll replace `"\n "` with `"&#xA;"`. But when DOM4J read XML, it will replace `"&#xA;"` with `"\n"`, and then `"\n"` with `Whitespace` in attribute according to XML specification. So you may lose your feed line. I use a intermediate variable to solve this problem, when you review the code you may curious about it.<br />
  ღ( ´･ᴗ･ )Finger Heart。

## 中文:
### 背景
  最近使用思维导图时，Windows 端使用 FreeMind，安卓端使用 Mindly，但二者文件格式不同。FreeMind 使用 .mm，并且不支持导出 .opml，恰好后者是 Mindly 支持导入的格式（更坑的是Mindly 不支持导入 .mm）。 <br />
  所以就写了这么一个小项目来回转换，希望能够帮助到有相同困扰的朋友。
### 环境
  JDK1.8.
### 运行
  执行下列命令: 
  `java -jar convertOpmlMm.jar xxx.mm` <br />
  请确保待转换文件放在 files/ 目录下，此目录与 convertOpmlMm.jar 同级。后面文件名称可以为 .opml 也可以为 .mm，代码会自动识别并生成对应文件。
### 导入
  `git clone git@github.com:ovictorain/opml2mm.git` <br />
  导入 Eclipse。（尚未在 IntelliJ IDEA and NetBean 上测试）
### Others
  Mindly（1.14） 版本导出 .opml 文件时会将换行符 `"\n"` 导出为 `"&#xA;"`。但 DOM4J 读取 XML 时会替换回去，并根据 XML 规范将属性中的换行符抹除。故你可能会发现导出的 .mm 文件部分内容未能换行。我使用了一个中间变量解决此类问题，如果对此处代码疑惑，那就是这个原因了。如果有更好方案或其他类似问题的解决办法，就提 PR 共同交流吧。 <br />
  ღ( ´･ᴗ･ )笔芯。
