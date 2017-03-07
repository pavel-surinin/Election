function getDistrictsCount(self) {
  axios
    .get('/district/all')
    .then(function(response){
      self.setState({
        districtsCount :  response.data,
      });
    })
    .catch(function(err){
      console.error('DistrictListContainer.getDistrict.axios.get.district', err);
    });
}

function getDistrict(self,page) {
  axios
    .get('/district/' + page + '/page')
    .then(function(response){
      EventEmitter.publish({ eventType: 'LogCounty' });
      self.setState({
        districtList :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('DistrictListContainer.getDistrict.axios.get.district', err);
    });
}

function getDistrictByLetter(self,letter) {
  axios
    .get('/district/' + letter + '/letter')
    .then(function(response){
      EventEmitter.publish({ eventType: 'LogCounty' });
      self.setState({
        districtList :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('DistrictListContainer.getDistrict.axios.get.district', err);
    });
}
var DistrictListContainer = React.createClass({
  getInitialState: function() {
    return {
      districtList : [],
      isLoading : true,
      deleteDistrictName : '',
      page : 0,
      districtsCount : 0,
      letter : 'a',
    };
  },
  componentDidMount: function() {
    if (this.props.location.query.page != null) {
      getDistrictsCount(this);
      getDistrict(this, this.props.location.query.page);
    } else if(this.props.location.query.letter != null){
      getDistrictByLetter(this, this.props.location.query.letter);
    } else {
      getDistrictsCount(this);
      getDistrict(this, this.state.page);
    }
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
  },
  componentWillReceiveProps: function(nextProps) {
    if (nextProps.location.query.page != null) {
      getDistrictsCount(this);
      getDistrict(this, nextProps.location.query.page);
      this.setState({page : nextProps.location.query.page});
    } else if(nextProps.location.query.letter != null){
      getDistrictByLetter(this, nextProps.location.query.letter);
      this.setState({letter : nextProps.location.query.letter});
    } else {
      getDistrictsCount(this);
      getDistrict(this, this.state.page);
    }
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
  },
  onHandleDelete: function(id, name) {
    var self = this;
    axios
      .delete('/district/'+ id)
      .then(function(response){
        getDistrict(self,self.state.page);
        self.setState({succesCreateText : '', deletedDistrictName : 'Apylinke ' + name + ' ištrinta.'});
      })
      .catch(function(err){
        console.error('DistrictListContainer.onHandleDelete.axios', err);
      });
  },

  render: function() {
    document.title='Administratoriaus Apylinkės Vaizdas Rinkimai 2017';
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      return (
        <DistrictListViewComponent
          districtList={this.state.districtList}
          onHandleDelete={this.onHandleDelete}
          succesCreateText={this.state.succesCreateText}
          deletedDistrictName={this.state.deletedDistrictName}
          districtsCount={this.state.districtsCount}
          currentPage={this.state.page}
          />
      );
    }

  }

});
window.DistrictListContainer = DistrictListContainer;
