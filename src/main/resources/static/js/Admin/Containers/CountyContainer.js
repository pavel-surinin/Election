function getCounty(self) {
  axios
    .get('/county')
    .then(function(response){
      self.setState({
        countyList :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('CountyContainer.getCounty.axios.get.county', err);
    });
}

function validate(self, code){
  if (code == 415) {fileErrorMesages.push('Netinkamas CSV failas');}
  if (code == 417) {numberErrorMesages.push('Partija su tokiu numeriu jau užregistruota');}
  if (code == 418) {nameErrorMesages.push('Partija su tokiu pavadinimu jau užregistruota');}
  if (code == 422) {fileErrorMesages.push('Blogi CSV duomenys');}
  self.setState({
    fileErrorMesages : fileErrorMesages,
    numberErrorMesages :numberErrorMesages,
    nameErrorMesages :nameErrorMesages,
  });
}

var CountyContainer = React.createClass({
  getInitialState: function() {
    return {
      countyList : [],
      isLoading : true,
      succesCreateText : '',
      countyId : null,
      file : null,
      fileErrorMesages : [],
    };
  },
  componentWillMount: function() {
    console.log(this.props);
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    getCounty(this);
    this.setState({fileErrorMesages : []});
  },

  onHandleFormAddSingleCandSubmit : function(event){
    this.setState({fileErrorMesages : []});
    event.preventDefault();
    console.log('submit');
    var self = this;
    //uploadfile constructing data
    var file = 'nofile.aaa';
    if (this.state.file != null) {
      file = this.state.file;}
    var data = new FormData();
    var header = { headers: {
      'Content-Type': 'multipart/form-data',
      'County-Id': this.state.countyId} };
    data.append('file', file);
    //validation
    var fileErrorMesages = [];
    if(!validation.checkForCsv(file.name)) {fileErrorMesages.push('Būtina prisegti *.csv formato failą');}
    if (fileErrorMesages.length == 0) {
      //TODO axios
      axios
      .put('/county', data, header)
      .then(function(response){
        self.context.router.push('/admin/county?succesCreateText=Partija ' + self.state.name + ' atnaujnta');
      })
      .catch(function (error) {
        if (error.response) {
          console.error(error.response.data.message);
          validate(self, error.response.status);
        }
      });
    } else {
      this.setState({fileErrorMesages : fileErrorMesages});
    }
  },
  onHandleDelete: function(i) {
    var self = this;
    event.preventDefault();
    axios
      .delete('/county/'+ i)
      .then(function(response){
        getCounty(self);
      })
      .catch(function(err){
        console.error('CountyContainer.onHandleDelete.axios', err);
      });
  },
  onFileChange : function(file, id){
    this.setState({
      countyId : id,
      file : file,
    });
  },
  onHandleAddClick : function(){
    this.setState({fileErrorMesages : []});
  },

  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      console.log(this.state.countyList);
      return (
        <CountyListViewComponent
          countyList={this.state.countyList}
          onHandleDelete={this.onHandleDelete}
          succesCreateText={this.state.succesCreateText}
          onFileChange={this.onFileChange}
          fileErrorMesages={this.state.fileErrorMesages}
          onHandleFormAddSingleCandSubmit={this.onHandleFormAddSingleCandSubmit}
          onHandleAddClick={this.onHandleAddClick}
        />
      );
    }
  }
});

window.CountyContainer = CountyContainer;
