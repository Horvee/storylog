# StoryLog! You business log other choice.

[中文README](https://github.com/MrHadess/storylog/blob/master/README-zh.md)

#### The story stems from an issue with disturbing log output！
Some businesses involve a large amount of log output, for example：
1. Troubleshoot abnormal problems in the later stage of the system
2. Multi-step data calculation error troubleshooting
3. Problem tracing（Sample toP: Third-party service provides wrong data,Cause calculation errors and lead to economic losses）Evidence of proof required
A lot of log printing will cause some problems, like to:One request will print a large number of logs of the calculation process, the request processing time is long but the print is more than one log

If you use the ELK log architecture, you may not only need extremely standardized logs! Too much job!!!

#### For this story log was born！
- **You can respond to a request**All the log will be group by tag,this tag has business info and then will be out put action info
- **You can target an interface**Story log beginning to ending for every time run function

#### Other features
- You can still use slf4j framework and out put log to story log
- You can custom save log plan
- Can be base onto micro service show request link

#### Advantage
1. Easy use in SpringBoot project
2. Configuration is simple,just change something code can be used to StoryLog

#### Focus
1. Everything requesting task will be saved to memory,until the request is processed! please don't out put binary data to log!
2. It not slf4j framework implement.This is log additional function,you can use it handler any request create by log! It can be batter help you find log easy

#### Dependency photo
![](https://github.com/Horvee/storylog/blob/master/DependencyPhoto.jpg?raw=true)







 