/**
 * Copyright (c) 2019-2021 Uber Technologies, Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uber.piranha;

import com.google.errorprone.BugCheckerRefactoringTestHelper;
import com.google.errorprone.ErrorProneFlags;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * This test suite tests core Piranha logic not fitting in any of the other test suites.
 *
 * <p>Additionally, we run the tests in resources/... from here.
 */
@RunWith(JUnit4.class)
public class SourceTest {
  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  /** TODO */
  @Test
  public void checkMethodRemovedLeavesNoWhitespaceTest() {
    ErrorProneFlags.Builder b = ErrorProneFlags.builder();
    b.putFlag("Piranha:FlagName", "STALE_FLAG");
    b.putFlag("Piranha:IsTreated", "true");
    b.putFlag("Piranha:Config", "config/properties.json");

    BugCheckerRefactoringTestHelper bcr =
        BugCheckerRefactoringTestHelper.newInstance(new XPFlagCleaner(b.build()), getClass());

    bcr = bcr.setArgs("-d", temporaryFolder.getRoot().getAbsolutePath());

    bcr.addInputLines(
            "TestExperimentName.java",
            "package com.uber.piranha;",
            "public enum TestExperimentName {",
            " STALE_FLAG",
            "}")
        .addOutputLines(
            "TestExperimentName.java",
            "package com.uber.piranha;",
            "public enum TestExperimentName {", // Ideally we would remove this too, fix later
            "}")
        .addInputLines(
            "XPFlagCleanerSinglePositiveCase.java",
            "package com.uber.piranha;",
            "import static com.uber.piranha.TestExperimentName" + ".STALE_FLAG;",
            "class XPFlagCleanerSinglePositiveCase {",
            " private XPTest experimentation;",
            " public boolean return_contains_stale_flag() {",
            "  System.out.println(\"Do cool stuff 1\");",
            "  if (experimentation.isToggleEnabled(TestExperimentName.STALE_FLAG)) {",
            "    return false;",
            "  }",
            "",
            "  System.out.println(\"Do cool stuff 2\");",
            "  System.out.println(\"Do cool stuff 3\");",
            "  System.out.println(\"Do cool stuff 4\");",
            "  System.out.println(\"Do cool stuff 5\");",
            "  return true;",
            " }",
            "}")
        .addOutputLines(
            "XPFlagCleanerSinglePositiveCase.java",
            "package com.uber.piranha;",
            "class XPFlagCleanerSinglePositiveCase {",
            " private XPTest experimentation;",
            " public boolean return_contains_stale_flag() {",
            "  System.out.println(\"Do cool stuff 1\");",
            "  return false;",
            "",
            " }",
            "}")
        .doTest(BugCheckerRefactoringTestHelper.TestMode.EXACT_TEXT_MATCH);
  }
}
