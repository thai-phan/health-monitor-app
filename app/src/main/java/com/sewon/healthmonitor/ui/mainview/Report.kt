package com.sewon.healthmonitor.ui.mainview

import com.sewon.healthmonitor.ui.courses.CoursesAppBar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sewon.healthmonitor.model.Course
import com.sewon.healthmonitor.model.courses
import com.sewon.healthmonitor.ui.common.CourseListItem
import com.sewon.healthmonitor.ui.courses.MyCourses
import com.sewon.healthmonitor.ui.theme.WhiteTheme

@Composable
fun Report(
    courses: List<Course>,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        item {
            CoursesAppBar()
        }
        itemsIndexed(
            items = courses,
            key = { _, course -> course.id }
        ) { index, course ->
            MyCourse(
                course = course,
                index = index,
                selectCourse = selectCourse
            )
        }
    }
}

@Composable
fun MyCourse(
    course: Course,
    index: Int,
    selectCourse: (Long) -> Unit
) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        val stagger = if (index % 2 == 0) 72.dp else 16.dp
        Spacer(modifier = Modifier.width(stagger))
        CourseListItem(
            course = course,
            onClick = { selectCourse(course.id) },
            shape = RoundedCornerShape(topStart = 24.dp),
            modifier = Modifier.height(96.dp)
        )
    }
}

@Preview(name = "My Courses")
@Composable
private fun MyCoursesPreview() {
    WhiteTheme() {
        MyCourses(
            courses = courses,
            selectCourse = { }
        )
    }
}
