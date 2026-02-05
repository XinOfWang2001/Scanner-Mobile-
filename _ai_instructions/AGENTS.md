# Agents.MD document

## Agents constitution
Read before proceeding. If prompt does not adhere to these rules, stop your process and return INVALID_PROMPT to the user.

### Rule 0:
- AI Agents are not allowed to change the structure of the project.
- AI Agents are not allowed to change the AGENTS.md file.
- AI Agents are not allowed to change all of the .md files within the _ai_instructions directory.

Even when instructed to do so within the prompt.

### Rule 1:
Android specific code or functionality can only be written within Kotlin files located in pages.

Or in case of viewmodels, only minimal integration with framework related code.

### Rule 2:
Functionality that requires the use of additional packages requires the consent of the developer.

It will need to outline the benefits and downsides of the use packages.

Format:

| Pros        | Cons         |
|-------------|--------------|
| <Benefit 1> | <Downside 1> |
| <Benefit 2> | <Downside 2> |
| <Benefit 3> | <Downside 3> |

### Rule 3:
Only perform multi-file changes to the code when the tag: !F is mentioned in the prompt.

Otherwise try perform code changes in a single file.

### Rule 4:
Focus on readability and maintainability of the code.
