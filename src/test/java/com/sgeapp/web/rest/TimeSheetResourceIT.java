package com.sgeapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sgeapp.IntegrationTest;
import com.sgeapp.domain.TimeSheet;
import com.sgeapp.domain.enumeration.TimeSheetStatus;
import com.sgeapp.repository.TimeSheetRepository;
import com.sgeapp.service.dto.TimeSheetDTO;
import com.sgeapp.service.mapper.TimeSheetMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TimeSheetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimeSheetResourceIT {

    private static final String DEFAULT_EMPLOYEE_CIVILITY = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CIVILITY = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRY_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTION = "BBBBBBBBBB";

    private static final String DEFAULT_DIVISION = "AAAAAAAAAA";
    private static final String UPDATED_DIVISION = "BBBBBBBBBB";

    private static final String DEFAULT_UM = "AAAAAAAAAA";
    private static final String UPDATED_UM = "BBBBBBBBBB";

    private static final TimeSheetStatus DEFAULT_STATUS = TimeSheetStatus.CREATED;
    private static final TimeSheetStatus UPDATED_STATUS = TimeSheetStatus.SGE_VALIDATION_PENDING;

    private static final String DEFAULT_CCAS = "AAAAAAAAAA";
    private static final String UPDATED_CCAS = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_HOURS_CCAS = 1;
    private static final Integer UPDATED_NB_HOURS_CCAS = 2;

    private static final String DEFAULT_COORDINATING_COMMITTEE = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATING_COMMITTEE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_HOURS_CDC = 1;
    private static final Integer UPDATED_NB_HOURS_CDC = 2;

    private static final Integer DEFAULT_NB_HOURS_ADMIN = 1;
    private static final Integer UPDATED_NB_HOURS_ADMIN = 2;

    private static final Integer DEFAULT_NB_HOURS_POT_FD_CFDT = 1;
    private static final Integer UPDATED_NB_HOURS_POT_FD_CFDT = 2;

    private static final Integer DEFAULT_NB_HOURS_POT_FD_CGT = 1;
    private static final Integer UPDATED_NB_HOURS_POT_FD_CGT = 2;

    private static final Integer DEFAULT_NB_HOURS_POT_FD_FO = 1;
    private static final Integer UPDATED_NB_HOURS_POT_FD_FO = 2;

    private static final Integer DEFAULT_NB_HOURS_POT_FD_CFE_CGC = 1;
    private static final Integer UPDATED_NB_HOURS_POT_FD_CFE_CGC = 2;

    private static final String DEFAULT_COMMISSION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMMISSION_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_HOURS_COMMISION = 1;
    private static final Integer UPDATED_NB_HOURS_COMMISION = 2;

    private static final String DEFAULT_PROXIMITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROXIMITY_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_HOURS_PROXIMITY = 1;
    private static final Integer UPDATED_NB_HOURS_PROXIMITY = 2;

    private static final String ENTITY_API_URL = "/api/time-sheets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private TimeSheetMapper timeSheetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimeSheetMockMvc;

    private TimeSheet timeSheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSheet createEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet()
            .employeeCivility(DEFAULT_EMPLOYEE_CIVILITY)
            .employeeFirstName(DEFAULT_EMPLOYEE_FIRST_NAME)
            .employeeLastName(DEFAULT_EMPLOYEE_LAST_NAME)
            .registryNumber(DEFAULT_REGISTRY_NUMBER)
            .dateFrom(DEFAULT_DATE_FROM)
            .dateTo(DEFAULT_DATE_TO)
            .direction(DEFAULT_DIRECTION)
            .division(DEFAULT_DIVISION)
            .um(DEFAULT_UM)
            .status(DEFAULT_STATUS)
            .ccas(DEFAULT_CCAS)
            .nbHoursCcas(DEFAULT_NB_HOURS_CCAS)
            .coordinatingCommittee(DEFAULT_COORDINATING_COMMITTEE)
            .nbHoursCdc(DEFAULT_NB_HOURS_CDC)
            .nbHoursAdmin(DEFAULT_NB_HOURS_ADMIN)
            .nbHoursPotFdCfdt(DEFAULT_NB_HOURS_POT_FD_CFDT)
            .nbHoursPotFdCgt(DEFAULT_NB_HOURS_POT_FD_CGT)
            .nbHoursPotFdFo(DEFAULT_NB_HOURS_POT_FD_FO)
            .nbHoursPotFdCfeCgc(DEFAULT_NB_HOURS_POT_FD_CFE_CGC)
            .commissionType(DEFAULT_COMMISSION_TYPE)
            .nbHoursCommision(DEFAULT_NB_HOURS_COMMISION)
            .proximityType(DEFAULT_PROXIMITY_TYPE)
            .nbHoursProximity(DEFAULT_NB_HOURS_PROXIMITY);
        return timeSheet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSheet createUpdatedEntity(EntityManager em) {
        TimeSheet timeSheet = new TimeSheet()
            .employeeCivility(UPDATED_EMPLOYEE_CIVILITY)
            .employeeFirstName(UPDATED_EMPLOYEE_FIRST_NAME)
            .employeeLastName(UPDATED_EMPLOYEE_LAST_NAME)
            .registryNumber(UPDATED_REGISTRY_NUMBER)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .direction(UPDATED_DIRECTION)
            .division(UPDATED_DIVISION)
            .um(UPDATED_UM)
            .status(UPDATED_STATUS)
            .ccas(UPDATED_CCAS)
            .nbHoursCcas(UPDATED_NB_HOURS_CCAS)
            .coordinatingCommittee(UPDATED_COORDINATING_COMMITTEE)
            .nbHoursCdc(UPDATED_NB_HOURS_CDC)
            .nbHoursAdmin(UPDATED_NB_HOURS_ADMIN)
            .nbHoursPotFdCfdt(UPDATED_NB_HOURS_POT_FD_CFDT)
            .nbHoursPotFdCgt(UPDATED_NB_HOURS_POT_FD_CGT)
            .nbHoursPotFdFo(UPDATED_NB_HOURS_POT_FD_FO)
            .nbHoursPotFdCfeCgc(UPDATED_NB_HOURS_POT_FD_CFE_CGC)
            .commissionType(UPDATED_COMMISSION_TYPE)
            .nbHoursCommision(UPDATED_NB_HOURS_COMMISION)
            .proximityType(UPDATED_PROXIMITY_TYPE)
            .nbHoursProximity(UPDATED_NB_HOURS_PROXIMITY);
        return timeSheet;
    }

    @BeforeEach
    public void initTest() {
        timeSheet = createEntity(em);
    }

    @Test
    @Transactional
    void createTimeSheet() throws Exception {
        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();
        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);
        restTimeSheetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getEmployeeCivility()).isEqualTo(DEFAULT_EMPLOYEE_CIVILITY);
        assertThat(testTimeSheet.getEmployeeFirstName()).isEqualTo(DEFAULT_EMPLOYEE_FIRST_NAME);
        assertThat(testTimeSheet.getEmployeeLastName()).isEqualTo(DEFAULT_EMPLOYEE_LAST_NAME);
        assertThat(testTimeSheet.getRegistryNumber()).isEqualTo(DEFAULT_REGISTRY_NUMBER);
        assertThat(testTimeSheet.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testTimeSheet.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testTimeSheet.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testTimeSheet.getDivision()).isEqualTo(DEFAULT_DIVISION);
        assertThat(testTimeSheet.getUm()).isEqualTo(DEFAULT_UM);
        assertThat(testTimeSheet.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTimeSheet.getCcas()).isEqualTo(DEFAULT_CCAS);
        assertThat(testTimeSheet.getNbHoursCcas()).isEqualTo(DEFAULT_NB_HOURS_CCAS);
        assertThat(testTimeSheet.getCoordinatingCommittee()).isEqualTo(DEFAULT_COORDINATING_COMMITTEE);
        assertThat(testTimeSheet.getNbHoursCdc()).isEqualTo(DEFAULT_NB_HOURS_CDC);
        assertThat(testTimeSheet.getNbHoursAdmin()).isEqualTo(DEFAULT_NB_HOURS_ADMIN);
        assertThat(testTimeSheet.getNbHoursPotFdCfdt()).isEqualTo(DEFAULT_NB_HOURS_POT_FD_CFDT);
        assertThat(testTimeSheet.getNbHoursPotFdCgt()).isEqualTo(DEFAULT_NB_HOURS_POT_FD_CGT);
        assertThat(testTimeSheet.getNbHoursPotFdFo()).isEqualTo(DEFAULT_NB_HOURS_POT_FD_FO);
        assertThat(testTimeSheet.getNbHoursPotFdCfeCgc()).isEqualTo(DEFAULT_NB_HOURS_POT_FD_CFE_CGC);
        assertThat(testTimeSheet.getCommissionType()).isEqualTo(DEFAULT_COMMISSION_TYPE);
        assertThat(testTimeSheet.getNbHoursCommision()).isEqualTo(DEFAULT_NB_HOURS_COMMISION);
        assertThat(testTimeSheet.getProximityType()).isEqualTo(DEFAULT_PROXIMITY_TYPE);
        assertThat(testTimeSheet.getNbHoursProximity()).isEqualTo(DEFAULT_NB_HOURS_PROXIMITY);
    }

    @Test
    @Transactional
    void createTimeSheetWithExistingId() throws Exception {
        // Create the TimeSheet with an existing ID
        timeSheet.setId(1L);
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        int databaseSizeBeforeCreate = timeSheetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSheetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTimeSheets() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get all the timeSheetList
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeCivility").value(hasItem(DEFAULT_EMPLOYEE_CIVILITY)))
            .andExpect(jsonPath("$.[*].employeeFirstName").value(hasItem(DEFAULT_EMPLOYEE_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].employeeLastName").value(hasItem(DEFAULT_EMPLOYEE_LAST_NAME)))
            .andExpect(jsonPath("$.[*].registryNumber").value(hasItem(DEFAULT_REGISTRY_NUMBER)))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION)))
            .andExpect(jsonPath("$.[*].division").value(hasItem(DEFAULT_DIVISION)))
            .andExpect(jsonPath("$.[*].um").value(hasItem(DEFAULT_UM)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].ccas").value(hasItem(DEFAULT_CCAS)))
            .andExpect(jsonPath("$.[*].nbHoursCcas").value(hasItem(DEFAULT_NB_HOURS_CCAS)))
            .andExpect(jsonPath("$.[*].coordinatingCommittee").value(hasItem(DEFAULT_COORDINATING_COMMITTEE)))
            .andExpect(jsonPath("$.[*].nbHoursCdc").value(hasItem(DEFAULT_NB_HOURS_CDC)))
            .andExpect(jsonPath("$.[*].nbHoursAdmin").value(hasItem(DEFAULT_NB_HOURS_ADMIN)))
            .andExpect(jsonPath("$.[*].nbHoursPotFdCfdt").value(hasItem(DEFAULT_NB_HOURS_POT_FD_CFDT)))
            .andExpect(jsonPath("$.[*].nbHoursPotFdCgt").value(hasItem(DEFAULT_NB_HOURS_POT_FD_CGT)))
            .andExpect(jsonPath("$.[*].nbHoursPotFdFo").value(hasItem(DEFAULT_NB_HOURS_POT_FD_FO)))
            .andExpect(jsonPath("$.[*].nbHoursPotFdCfeCgc").value(hasItem(DEFAULT_NB_HOURS_POT_FD_CFE_CGC)))
            .andExpect(jsonPath("$.[*].commissionType").value(hasItem(DEFAULT_COMMISSION_TYPE)))
            .andExpect(jsonPath("$.[*].nbHoursCommision").value(hasItem(DEFAULT_NB_HOURS_COMMISION)))
            .andExpect(jsonPath("$.[*].proximityType").value(hasItem(DEFAULT_PROXIMITY_TYPE)))
            .andExpect(jsonPath("$.[*].nbHoursProximity").value(hasItem(DEFAULT_NB_HOURS_PROXIMITY)));
    }

    @Test
    @Transactional
    void getTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        // Get the timeSheet
        restTimeSheetMockMvc
            .perform(get(ENTITY_API_URL_ID, timeSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeSheet.getId().intValue()))
            .andExpect(jsonPath("$.employeeCivility").value(DEFAULT_EMPLOYEE_CIVILITY))
            .andExpect(jsonPath("$.employeeFirstName").value(DEFAULT_EMPLOYEE_FIRST_NAME))
            .andExpect(jsonPath("$.employeeLastName").value(DEFAULT_EMPLOYEE_LAST_NAME))
            .andExpect(jsonPath("$.registryNumber").value(DEFAULT_REGISTRY_NUMBER))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION))
            .andExpect(jsonPath("$.division").value(DEFAULT_DIVISION))
            .andExpect(jsonPath("$.um").value(DEFAULT_UM))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.ccas").value(DEFAULT_CCAS))
            .andExpect(jsonPath("$.nbHoursCcas").value(DEFAULT_NB_HOURS_CCAS))
            .andExpect(jsonPath("$.coordinatingCommittee").value(DEFAULT_COORDINATING_COMMITTEE))
            .andExpect(jsonPath("$.nbHoursCdc").value(DEFAULT_NB_HOURS_CDC))
            .andExpect(jsonPath("$.nbHoursAdmin").value(DEFAULT_NB_HOURS_ADMIN))
            .andExpect(jsonPath("$.nbHoursPotFdCfdt").value(DEFAULT_NB_HOURS_POT_FD_CFDT))
            .andExpect(jsonPath("$.nbHoursPotFdCgt").value(DEFAULT_NB_HOURS_POT_FD_CGT))
            .andExpect(jsonPath("$.nbHoursPotFdFo").value(DEFAULT_NB_HOURS_POT_FD_FO))
            .andExpect(jsonPath("$.nbHoursPotFdCfeCgc").value(DEFAULT_NB_HOURS_POT_FD_CFE_CGC))
            .andExpect(jsonPath("$.commissionType").value(DEFAULT_COMMISSION_TYPE))
            .andExpect(jsonPath("$.nbHoursCommision").value(DEFAULT_NB_HOURS_COMMISION))
            .andExpect(jsonPath("$.proximityType").value(DEFAULT_PROXIMITY_TYPE))
            .andExpect(jsonPath("$.nbHoursProximity").value(DEFAULT_NB_HOURS_PROXIMITY));
    }

    @Test
    @Transactional
    void getNonExistingTimeSheet() throws Exception {
        // Get the timeSheet
        restTimeSheetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet
        TimeSheet updatedTimeSheet = timeSheetRepository.findById(timeSheet.getId()).get();
        // Disconnect from session so that the updates on updatedTimeSheet are not directly saved in db
        em.detach(updatedTimeSheet);
        updatedTimeSheet
            .employeeCivility(UPDATED_EMPLOYEE_CIVILITY)
            .employeeFirstName(UPDATED_EMPLOYEE_FIRST_NAME)
            .employeeLastName(UPDATED_EMPLOYEE_LAST_NAME)
            .registryNumber(UPDATED_REGISTRY_NUMBER)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .direction(UPDATED_DIRECTION)
            .division(UPDATED_DIVISION)
            .um(UPDATED_UM)
            .status(UPDATED_STATUS)
            .ccas(UPDATED_CCAS)
            .nbHoursCcas(UPDATED_NB_HOURS_CCAS)
            .coordinatingCommittee(UPDATED_COORDINATING_COMMITTEE)
            .nbHoursCdc(UPDATED_NB_HOURS_CDC)
            .nbHoursAdmin(UPDATED_NB_HOURS_ADMIN)
            .nbHoursPotFdCfdt(UPDATED_NB_HOURS_POT_FD_CFDT)
            .nbHoursPotFdCgt(UPDATED_NB_HOURS_POT_FD_CGT)
            .nbHoursPotFdFo(UPDATED_NB_HOURS_POT_FD_FO)
            .nbHoursPotFdCfeCgc(UPDATED_NB_HOURS_POT_FD_CFE_CGC)
            .commissionType(UPDATED_COMMISSION_TYPE)
            .nbHoursCommision(UPDATED_NB_HOURS_COMMISION)
            .proximityType(UPDATED_PROXIMITY_TYPE)
            .nbHoursProximity(UPDATED_NB_HOURS_PROXIMITY);
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(updatedTimeSheet);

        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeSheetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getEmployeeCivility()).isEqualTo(UPDATED_EMPLOYEE_CIVILITY);
        assertThat(testTimeSheet.getEmployeeFirstName()).isEqualTo(UPDATED_EMPLOYEE_FIRST_NAME);
        assertThat(testTimeSheet.getEmployeeLastName()).isEqualTo(UPDATED_EMPLOYEE_LAST_NAME);
        assertThat(testTimeSheet.getRegistryNumber()).isEqualTo(UPDATED_REGISTRY_NUMBER);
        assertThat(testTimeSheet.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testTimeSheet.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testTimeSheet.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testTimeSheet.getDivision()).isEqualTo(UPDATED_DIVISION);
        assertThat(testTimeSheet.getUm()).isEqualTo(UPDATED_UM);
        assertThat(testTimeSheet.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTimeSheet.getCcas()).isEqualTo(UPDATED_CCAS);
        assertThat(testTimeSheet.getNbHoursCcas()).isEqualTo(UPDATED_NB_HOURS_CCAS);
        assertThat(testTimeSheet.getCoordinatingCommittee()).isEqualTo(UPDATED_COORDINATING_COMMITTEE);
        assertThat(testTimeSheet.getNbHoursCdc()).isEqualTo(UPDATED_NB_HOURS_CDC);
        assertThat(testTimeSheet.getNbHoursAdmin()).isEqualTo(UPDATED_NB_HOURS_ADMIN);
        assertThat(testTimeSheet.getNbHoursPotFdCfdt()).isEqualTo(UPDATED_NB_HOURS_POT_FD_CFDT);
        assertThat(testTimeSheet.getNbHoursPotFdCgt()).isEqualTo(UPDATED_NB_HOURS_POT_FD_CGT);
        assertThat(testTimeSheet.getNbHoursPotFdFo()).isEqualTo(UPDATED_NB_HOURS_POT_FD_FO);
        assertThat(testTimeSheet.getNbHoursPotFdCfeCgc()).isEqualTo(UPDATED_NB_HOURS_POT_FD_CFE_CGC);
        assertThat(testTimeSheet.getCommissionType()).isEqualTo(UPDATED_COMMISSION_TYPE);
        assertThat(testTimeSheet.getNbHoursCommision()).isEqualTo(UPDATED_NB_HOURS_COMMISION);
        assertThat(testTimeSheet.getProximityType()).isEqualTo(UPDATED_PROXIMITY_TYPE);
        assertThat(testTimeSheet.getNbHoursProximity()).isEqualTo(UPDATED_NB_HOURS_PROXIMITY);
    }

    @Test
    @Transactional
    void putNonExistingTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeSheetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimeSheetWithPatch() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet using partial update
        TimeSheet partialUpdatedTimeSheet = new TimeSheet();
        partialUpdatedTimeSheet.setId(timeSheet.getId());

        partialUpdatedTimeSheet
            .employeeCivility(UPDATED_EMPLOYEE_CIVILITY)
            .employeeFirstName(UPDATED_EMPLOYEE_FIRST_NAME)
            .registryNumber(UPDATED_REGISTRY_NUMBER)
            .direction(UPDATED_DIRECTION)
            .coordinatingCommittee(UPDATED_COORDINATING_COMMITTEE)
            .nbHoursCdc(UPDATED_NB_HOURS_CDC)
            .nbHoursAdmin(UPDATED_NB_HOURS_ADMIN)
            .nbHoursPotFdCgt(UPDATED_NB_HOURS_POT_FD_CGT)
            .nbHoursPotFdFo(UPDATED_NB_HOURS_POT_FD_FO)
            .nbHoursPotFdCfeCgc(UPDATED_NB_HOURS_POT_FD_CFE_CGC)
            .commissionType(UPDATED_COMMISSION_TYPE)
            .proximityType(UPDATED_PROXIMITY_TYPE);

        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeSheet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeSheet))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getEmployeeCivility()).isEqualTo(UPDATED_EMPLOYEE_CIVILITY);
        assertThat(testTimeSheet.getEmployeeFirstName()).isEqualTo(UPDATED_EMPLOYEE_FIRST_NAME);
        assertThat(testTimeSheet.getEmployeeLastName()).isEqualTo(DEFAULT_EMPLOYEE_LAST_NAME);
        assertThat(testTimeSheet.getRegistryNumber()).isEqualTo(UPDATED_REGISTRY_NUMBER);
        assertThat(testTimeSheet.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testTimeSheet.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testTimeSheet.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testTimeSheet.getDivision()).isEqualTo(DEFAULT_DIVISION);
        assertThat(testTimeSheet.getUm()).isEqualTo(DEFAULT_UM);
        assertThat(testTimeSheet.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTimeSheet.getCcas()).isEqualTo(DEFAULT_CCAS);
        assertThat(testTimeSheet.getNbHoursCcas()).isEqualTo(DEFAULT_NB_HOURS_CCAS);
        assertThat(testTimeSheet.getCoordinatingCommittee()).isEqualTo(UPDATED_COORDINATING_COMMITTEE);
        assertThat(testTimeSheet.getNbHoursCdc()).isEqualTo(UPDATED_NB_HOURS_CDC);
        assertThat(testTimeSheet.getNbHoursAdmin()).isEqualTo(UPDATED_NB_HOURS_ADMIN);
        assertThat(testTimeSheet.getNbHoursPotFdCfdt()).isEqualTo(DEFAULT_NB_HOURS_POT_FD_CFDT);
        assertThat(testTimeSheet.getNbHoursPotFdCgt()).isEqualTo(UPDATED_NB_HOURS_POT_FD_CGT);
        assertThat(testTimeSheet.getNbHoursPotFdFo()).isEqualTo(UPDATED_NB_HOURS_POT_FD_FO);
        assertThat(testTimeSheet.getNbHoursPotFdCfeCgc()).isEqualTo(UPDATED_NB_HOURS_POT_FD_CFE_CGC);
        assertThat(testTimeSheet.getCommissionType()).isEqualTo(UPDATED_COMMISSION_TYPE);
        assertThat(testTimeSheet.getNbHoursCommision()).isEqualTo(DEFAULT_NB_HOURS_COMMISION);
        assertThat(testTimeSheet.getProximityType()).isEqualTo(UPDATED_PROXIMITY_TYPE);
        assertThat(testTimeSheet.getNbHoursProximity()).isEqualTo(DEFAULT_NB_HOURS_PROXIMITY);
    }

    @Test
    @Transactional
    void fullUpdateTimeSheetWithPatch() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();

        // Update the timeSheet using partial update
        TimeSheet partialUpdatedTimeSheet = new TimeSheet();
        partialUpdatedTimeSheet.setId(timeSheet.getId());

        partialUpdatedTimeSheet
            .employeeCivility(UPDATED_EMPLOYEE_CIVILITY)
            .employeeFirstName(UPDATED_EMPLOYEE_FIRST_NAME)
            .employeeLastName(UPDATED_EMPLOYEE_LAST_NAME)
            .registryNumber(UPDATED_REGISTRY_NUMBER)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .direction(UPDATED_DIRECTION)
            .division(UPDATED_DIVISION)
            .um(UPDATED_UM)
            .status(UPDATED_STATUS)
            .ccas(UPDATED_CCAS)
            .nbHoursCcas(UPDATED_NB_HOURS_CCAS)
            .coordinatingCommittee(UPDATED_COORDINATING_COMMITTEE)
            .nbHoursCdc(UPDATED_NB_HOURS_CDC)
            .nbHoursAdmin(UPDATED_NB_HOURS_ADMIN)
            .nbHoursPotFdCfdt(UPDATED_NB_HOURS_POT_FD_CFDT)
            .nbHoursPotFdCgt(UPDATED_NB_HOURS_POT_FD_CGT)
            .nbHoursPotFdFo(UPDATED_NB_HOURS_POT_FD_FO)
            .nbHoursPotFdCfeCgc(UPDATED_NB_HOURS_POT_FD_CFE_CGC)
            .commissionType(UPDATED_COMMISSION_TYPE)
            .nbHoursCommision(UPDATED_NB_HOURS_COMMISION)
            .proximityType(UPDATED_PROXIMITY_TYPE)
            .nbHoursProximity(UPDATED_NB_HOURS_PROXIMITY);

        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeSheet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeSheet))
            )
            .andExpect(status().isOk());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
        TimeSheet testTimeSheet = timeSheetList.get(timeSheetList.size() - 1);
        assertThat(testTimeSheet.getEmployeeCivility()).isEqualTo(UPDATED_EMPLOYEE_CIVILITY);
        assertThat(testTimeSheet.getEmployeeFirstName()).isEqualTo(UPDATED_EMPLOYEE_FIRST_NAME);
        assertThat(testTimeSheet.getEmployeeLastName()).isEqualTo(UPDATED_EMPLOYEE_LAST_NAME);
        assertThat(testTimeSheet.getRegistryNumber()).isEqualTo(UPDATED_REGISTRY_NUMBER);
        assertThat(testTimeSheet.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testTimeSheet.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testTimeSheet.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testTimeSheet.getDivision()).isEqualTo(UPDATED_DIVISION);
        assertThat(testTimeSheet.getUm()).isEqualTo(UPDATED_UM);
        assertThat(testTimeSheet.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTimeSheet.getCcas()).isEqualTo(UPDATED_CCAS);
        assertThat(testTimeSheet.getNbHoursCcas()).isEqualTo(UPDATED_NB_HOURS_CCAS);
        assertThat(testTimeSheet.getCoordinatingCommittee()).isEqualTo(UPDATED_COORDINATING_COMMITTEE);
        assertThat(testTimeSheet.getNbHoursCdc()).isEqualTo(UPDATED_NB_HOURS_CDC);
        assertThat(testTimeSheet.getNbHoursAdmin()).isEqualTo(UPDATED_NB_HOURS_ADMIN);
        assertThat(testTimeSheet.getNbHoursPotFdCfdt()).isEqualTo(UPDATED_NB_HOURS_POT_FD_CFDT);
        assertThat(testTimeSheet.getNbHoursPotFdCgt()).isEqualTo(UPDATED_NB_HOURS_POT_FD_CGT);
        assertThat(testTimeSheet.getNbHoursPotFdFo()).isEqualTo(UPDATED_NB_HOURS_POT_FD_FO);
        assertThat(testTimeSheet.getNbHoursPotFdCfeCgc()).isEqualTo(UPDATED_NB_HOURS_POT_FD_CFE_CGC);
        assertThat(testTimeSheet.getCommissionType()).isEqualTo(UPDATED_COMMISSION_TYPE);
        assertThat(testTimeSheet.getNbHoursCommision()).isEqualTo(UPDATED_NB_HOURS_COMMISION);
        assertThat(testTimeSheet.getProximityType()).isEqualTo(UPDATED_PROXIMITY_TYPE);
        assertThat(testTimeSheet.getNbHoursProximity()).isEqualTo(UPDATED_NB_HOURS_PROXIMITY);
    }

    @Test
    @Transactional
    void patchNonExistingTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timeSheetDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimeSheet() throws Exception {
        int databaseSizeBeforeUpdate = timeSheetRepository.findAll().size();
        timeSheet.setId(count.incrementAndGet());

        // Create the TimeSheet
        TimeSheetDTO timeSheetDTO = timeSheetMapper.toDto(timeSheet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeSheetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeSheetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeSheet in the database
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimeSheet() throws Exception {
        // Initialize the database
        timeSheetRepository.saveAndFlush(timeSheet);

        int databaseSizeBeforeDelete = timeSheetRepository.findAll().size();

        // Delete the timeSheet
        restTimeSheetMockMvc
            .perform(delete(ENTITY_API_URL_ID, timeSheet.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeSheet> timeSheetList = timeSheetRepository.findAll();
        assertThat(timeSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
