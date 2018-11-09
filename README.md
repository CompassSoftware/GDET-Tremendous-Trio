# GitHub Data Extraction Tool (GDET)

This tool allows the easy extraction of GitHub repository data, such as commits, branches, issues, etc.  The tool is currently under development, with more functionality coming every day.

# Configuring GitHub Authentication

GDET currently pulls GitHub credentials directly from a file at `~/.github`.  In order to prepare for execution, you should create this file with the following format:

```
login=<username>
password=<password>
```

Once this is in place, you're good to go!

# Running GDET

Currently, GDET can be built and installed using the following command from the gdettt directory:

`mvn clean install -U`

And you can execute GDET using the following command from the gdettt directory:

`mvn exec:java`
