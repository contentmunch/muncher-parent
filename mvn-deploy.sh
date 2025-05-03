#!/bin/bash

set -euo pipefail
#CONFIG: ADD bom config
BOM_MODULE="muncher-bom"
PARENT_POM="pom.xml"
BOM_COORDINATES="com.contentmunch:muncher-bom"

# Step 1: Verify the build
echo "🛠️ Verifying build with quality profile..."
mvn -B verify -Pquality -Dgpg.skip=true

# Step 2: Bump minor version
echo "🔢 Bumping minor version..."
CURRENT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
IFS='.' read -r MAJOR MINOR PATCH <<< "$CURRENT_VERSION"
NEW_VERSION="${MAJOR}.$((MINOR + 1)).0"

#Step 2a: Bump BOM module version
echo "📘 Bumping BOM version to $NEW_VERSION in $BOM_MODULE..."
pushd "$BOM_MODULE" > /dev/null
mvn --batch-mode versions:set -DnewVersion=$NEW_VERSION
mvn versions:commit
popd > /dev/null

# Step 2b: Update parent POM to use new BOM version
echo "🧩 Updating parent POM to use BOM version $NEW_VERSION..."
mvn --batch-mode versions:use-dep-version -Dincludes=$BOM_COORDINATES -DdepVersion=$NEW_VERSION -DforceVersion=true -DgenerateBackupPoms=false

# Step 2c: Set project version
echo "📝 Setting parent version to $NEW_VERSION..."
mvn --batch-mode versions:set -DnewVersion=$NEW_VERSION
mvn versions:commit

echo "✅ New version set: $NEW_VERSION"

# Step 3: Commit and push version bump
echo "📦 Committing version bump..."

git commit -am "chore(release): bump to version $NEW_VERSION"
git push origin main

# Step 4: Deploy to Sonatype
echo "🚀 Deploying to Sonatype..."
mvn deploy -DskipTests

echo "🎉 Release complete: $NEW_VERSION"
