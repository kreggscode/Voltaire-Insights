@echo off
echo Pushing Voltaire Insights to GitHub...

REM Add all changes
git add .

REM Check if there are changes to commit
for /f %%i in ('git status --porcelain') do set HAS_CHANGES=1

if defined HAS_CHANGES (
    REM Prompt for commit message
    set /p COMMIT_MSG="Enter commit message: "
    if "%COMMIT_MSG%"=="" set COMMIT_MSG="Update Voltaire Insights"

    REM Commit changes
    git commit -m "%COMMIT_MSG%"
    echo Changes committed successfully!
) else (
    echo No changes to commit.
)

REM Push to GitHub
git push origin main

if %ERRORLEVEL% EQU 0 (
    echo Successfully pushed to GitHub!
    echo Repository: https://github.com/kreggscode/Voltaire-Insights
) else (
    echo Push failed. Check your GitHub credentials or network connection.
    echo You may need to set up authentication (personal access token).
)

pause
