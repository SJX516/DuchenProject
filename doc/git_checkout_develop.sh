#!/bin/bash
echo "================== git checkout =================="
# 接收路径参数
cd /Users/netease/Desktop/workspace/study-project-ykt/
echo "+++++++ checkout: ${PWD} ++++++++"

pathList=(
#-----------------BASE
        android-horizonal-listview-library  master
        android-support-design              master

        android-nim-uikit-lib               master
        android-xdownload-lib               master
        netease-skin-switcher               master
        android-support-v7-appcompat        master

        netease-url-interceptor             master


#-----------------标准版
        lib-study-nim                       master
        lib-module-personal-content         master

        lib-module-pay                      master

        lib-study-core                      release/stage_four

        lib-module-unit-page                feature/stage_five
        edu-android-boxes                   feature/stage_five
        lib-module-browser                  feature/stage_five
        edu-android-framework               feature/stage_five

        study-android-phone                 feature/stage_five
        lib-study-live                      feature/stage_five
        lib-study-player                    feature/stage_five
        lib-module-coursedetail-box         feature/stage_five

)

function checkout() {
	num=${#pathList[@]};

	echo 'project count ' $[$num / 2];
	for (( i = 0; i < $num; i++ )); do
		action=$(($i%2))
		if [ $action -eq 0 ] 
		then
			echo "---------------"$(($[$i/2]+1))/$[$num / 2]" "${pathList[$i]}"---------------";
			path=${pathList[$i]}
			branch=${pathList[$i+1]}	
			echo "---------------"path=$path, branch=$branch"---------------";	
			cd $path
			git fetch	
			git checkout $branch
			git pull --rebase
			cd ../
        fi 

	done
}

# cd ${PWD}/${pathList[$i]}
# git checkout develop
# cd ../

checkout;
