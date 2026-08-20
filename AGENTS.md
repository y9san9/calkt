# Repository workflow

## GitHub issue fixes

When the user asks to fix a GitHub issue in this repository, treat that request as standing authorization to complete the following workflow without asking for separate confirmations at each step:

1. Inspect the issue and the current worktree. Preserve unrelated user changes.
2. Synchronize with `origin/main` when it is safe, then create or reuse a dedicated `codex/issue-<number>-<slug>` branch.
3. Implement the fix and run tests appropriate to the affected modules, followed by the broader project checks when practical.
4. Stage only files that belong to the issue. Never use `git add .`, `git add -A`, or `git add --all`.
5. Create a descriptive commit, push the issue branch to `origin`, and open a draft pull request targeting `main`.
6. Include `Closes #<number>` in the pull request body and report the pull request URL and test results to the user.

The issue-fix request explicitly authorizes staging the task files, committing, pushing the issue branch, and creating the draft pull request. Do not request separate approval for those actions.

This standing authorization does not include merging pull requests, force-pushing, deleting branches, creating tags or releases, closing issues without a merged pull request, or including unrelated changes. Ask before performing any of those actions.

## GitHub CLI on macOS

Run `gh` commands that depend on GitHub authentication, the macOS Keychain, or network access outside the sandbox. A sandboxed `gh auth status` can incorrectly report an invalid token because the sandbox cannot access the Keychain; retry outside the sandbox before asking the user to authenticate again.
