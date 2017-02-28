var CandidateContainer = React.createClass({

  getInitialState: function() {
    return {
      candidateList : [],
      isLoading : true,
      succesCreateText: '',
    };
  },

  componentWillMount: function() {
    var self = this;
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    axios
      .get('/candidate')
      .then(function(response){
        self.setState({
          candidateList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
          console.error('CandidateContainer.componentWillMount.axios.get.candidate', err);
        });
  },

  render: function() {
    document.title='Administratoriaus Kandidatų Vaizdas Rinkimai 2017';
    console.log('Candidate container THIS', this);
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      return (
        <CandidatesListViewComponent
          candidateList={this.state.candidateList}
          succesCreateText={this.state.succesCreateText}
        />
      );
    }

  }

});

window.CandidateContainer = CandidateContainer;
